package com.elephant.searchspacepictures.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elephant.data.repository.NasaApiImplementation
import com.elephant.domain.usecase.CaseResult
import com.elephant.domain.usecase.GetSearchQueryJSON
import com.elephant.searchspacepictures.models.ResponseImages
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    private val searchRepository = NasaApiImplementation()
    private val getSearchQueryJSON = GetSearchQueryJSON(searchRepository)

    private val _state = MutableLiveData(MainScreenState())
    val state: LiveData<MainScreenState> = _state

    private val currentState: MainScreenState
        get() = state.value!!

    fun getPictures(choiceSearch: String) {
        viewModelScope.launch {
            getSearchQueryJSON(choiceSearch)
        }
    }

    private suspend fun getSearchQueryJSON(choiceSearch: String) {
        getSearchQueryJSON.invoke(choiceSearch).collect { result ->
            when (result) {
                is CaseResult.Success -> {
                    if (result.response.items.isNotEmpty()) {
                        _state.value = currentState.copy(loaded = true,
                            page = 1,
                            result.response.items.map { item ->
                                ResponseImages(
                                    previewImage = item.links.first().href,
                                    originLinkForList = item.href.replace(
                                        "https://images-assets.nasa.gov/",
                                        ""
                                    )
                                )
                            })
                    }
                }
                else -> {}
            }
        }
    }

//    private fun getList(list: List<ResponseImages>): Flow<ResponseImages> = flow {
//         currentState.listResponseImages.map { responseImage ->
//            getSearchQueryPictures.invoke(responseImage.originLinkForList)
//                .collect { result ->
//                    when (result) {
//                        is CaseResult.Success -> {
//                            if (result.response.isNotEmpty()) {
//                                emit(ResponseImages(
//                                    responseImage.previewImage,
//                                    responseImage.originLinkForList,
//                                    result.response.first {
//                                        regexOrig.containsMatchIn(it)
//                                    }))
//                            }
//                        }
//                        else -> {}
//                    }
//                }
//        }
//    }
}