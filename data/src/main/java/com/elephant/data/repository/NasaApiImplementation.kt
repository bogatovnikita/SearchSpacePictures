package com.elephant.data.repository

import com.elephant.data.retrofit.NasaApi
import com.elephant.domain.model.CollectionLink
import com.elephant.domain.model.Item
import com.elephant.domain.model.ItemLink
import com.elephant.domain.model.NasaServerModel
import com.elephant.domain.repositories.SearchRepository
import com.elephant.domain.wrappers.CallbackNetwork
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NasaApiImplementation : SearchRepository {
    private val retrofit = Retrofit.Builder()
    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS).build()

    override suspend fun getSearchQueryJSON(search: String): CallbackNetwork<NasaServerModel, String> =
        withContext(Dispatchers.IO) {
            try {
                val response = getRetrofitImplementationForSearch().getQuerySearch(
                    search = search,
                    type = "image",
                    page = 1
                )
                if (response.isSuccessful && response.body() != null) {
                    CallbackNetwork.Success(
                        NasaServerModel(
                            response.body()!!.collection.href,
                            response.body()!!.collection.items.map { item ->
                                Item(
                                    href = item.href,
                                    links = item.links.map {
                                        ItemLink(
                                            it.href
                                        )
                                    }
                                )
                            },
                            response.body()!!.collection.links.map {
                                CollectionLink(
                                    rel = it.rel,
                                    prompt = it.prompt,
                                    href = it.href
                                )
                            }
                        )
                    )
                } else {
                    CallbackNetwork.Failure(response.message())
                }
            } catch (e: Exception) {
                CallbackNetwork.Failure(e.toString())
            }
        }

    override suspend fun getSearchQueryPictures(url: String): CallbackNetwork<List<String>, String> =
        withContext(Dispatchers.IO) {
            try {
                val response = getRetrofitImplementationForPicture().getPicture(link = url)
                if (response.isSuccessful && response.body() != null) {
                    CallbackNetwork.Success(
                        response.body()!!
                    )
                } else {
                    CallbackNetwork.Failure(response.message())
                }
            } catch (e: Exception) {
                CallbackNetwork.Failure(e.toString())
            }
        }

    private fun getRetrofitImplementationForSearch(): NasaApi {
        retrofit
            .baseUrl("https://images-api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        return retrofit.build().create(NasaApi::class.java)
    }

    private fun getRetrofitImplementationForPicture(): NasaApi {
        retrofit
            .baseUrl("https://images-assets.nasa.gov/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        return retrofit.build().create(NasaApi::class.java)
    }
}