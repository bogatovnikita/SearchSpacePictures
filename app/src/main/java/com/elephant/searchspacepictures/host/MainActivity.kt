package com.elephant.searchspacepictures.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.elephant.data.repository.NasaApiImplementation
import com.elephant.domain.model.Item
import com.elephant.domain.usecase.CaseResult
import com.elephant.domain.usecase.GetSearchQueryJSON
import com.elephant.domain.usecase.GetSearchQueryPictures
import com.elephant.searchspacepictures.R
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
//    private var temp = mutableListOf<List<Item>>()
//    private var temp2 = mutableListOf<String>()
//    private var temp3 = mutableListOf<List<String>>()
//    private var temp4 = mutableListOf<String>()
//    private val regex = "orig".toRegex()
//
//    private val searchRepository = NasaApiImplementation()
//    private val useCase = GetSearchQueryJSON(searchRepository)
//    private val useCase2 = GetSearchQueryPictures(searchRepository)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        lifecycleScope.launch {
//            useCase.invoke("earth").collect { result ->
//                when (result) {
//                    is CaseResult.Success -> {
//                        if (result.response.items.isNotEmpty()) {
//                            temp.add(result.response.items)
//                        }
//                    }
//                    else -> {}
//                }
//            }
//            Log.e("pie", "onCreate: temp=$temp")
//            Log.e("pie", "onCreate: temp=${temp.size}")
//
//
//
//            temp.forEach { item ->
//                item.forEach {
//                    temp2.add((it.href).replace("https://images-assets.nasa.gov/", ""))
//                }
//            }
//            Log.e("pie", "onCreate: temp2=$temp2")
//            Log.e("pie", "onCreate: temp2=${temp2.size}")
//
//
//            temp2.forEach {
//                useCase2.invoke(it).collect { result ->
//                    when (result) {
//                        is CaseResult.Success -> {
//                            if (result.response.isNotEmpty()) {
//                                temp3.add(result.response)
//                            }
//                        }
//                        else -> {}
//                    }
//
//                }
//            }
//
//            Log.e("pie", "onCreate: temp3=$temp3")
//            Log.e("pie", "onCreate: temp3=${temp3.size}")
//
//            temp3.forEach { item ->
//                item.forEach {
//                    if (regex.containsMatchIn(it)) {
//                        temp4.add(it)
//                    }
//                }
//            }
//
//            Log.e("pie", "onCreate: temp4=$temp4")
//            Log.e("pie", "onCreate: temp4=${temp4.size}")
//        }
