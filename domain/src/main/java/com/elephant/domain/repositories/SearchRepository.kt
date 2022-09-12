package com.elephant.domain.repositories

import com.elephant.domain.model.NasaServerModel
import com.elephant.domain.wrappers.CallbackNetwork

interface SearchRepository {
    suspend fun getSearchQueryJSON(search:String,page:Int): CallbackNetwork<NasaServerModel, String>
    suspend fun getSearchQueryPictures(url:String): CallbackNetwork<List<String>, String>
}