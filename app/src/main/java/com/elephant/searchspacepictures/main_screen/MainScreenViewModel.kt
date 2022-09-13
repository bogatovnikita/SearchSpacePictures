package com.elephant.searchspacepictures.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elephant.data.repository.NasaApiImplementation
import com.elephant.domain.usecase.CaseResult
import com.elephant.domain.usecase.GetSearchQueryJSON
import com.elephant.searchspacepictures.models.ResponseUrlPictures
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    private val searchRepository = NasaApiImplementation()
    private val getSearchQueryJSON = GetSearchQueryJSON(searchRepository)

    private val _state = MutableLiveData(MainScreenState())
    val state: LiveData<MainScreenState> = _state

    private val currentState: MainScreenState
        get() = state.value!!

    fun getPictures(choiceSearch: String, page: Int) {
        _state.value = currentState.copy(loaded = false, notResponse = false)
        viewModelScope.launch {
            getSearchQueryJSON(choiceSearch, page)
        }
    }

    private suspend fun getSearchQueryJSON(choiceSearch: String, page: Int) {
        getSearchQueryJSON.invoke(choiceSearch, page).collect { result ->
            when (result) {
                is CaseResult.Success -> {
                    if (result.response.items.isNotEmpty()) {
                        _state.value = currentState.copy(
                            loaded = true,
                            page = page,
                            listResponsePicture = result.response.items.map { item ->
                                ResponseUrlPictures(
                                    previewImage = item.links.first().href,
                                    originLinkForList = item.href.replace(
                                        "https://images-assets.nasa.gov/",
                                        ""
                                    )
                                )
                            }, totalPictures = result.response.metadata
                        )
                    }
                }
                is CaseResult.Failure -> {
                    _state.value = currentState.copy(notResponse = true)
                }
                else -> {}
            }
        }
    }

    fun upPage(choiceSearch: String) {
        if (currentState.totalPictures / 100 > currentState.page) {
            _state.value = currentState.copy(page = currentState.page + 1)
            getPictures(choiceSearch, currentState.page)
        }
    }

    fun downPage(choiceSearch: String) {
        if (currentState.page > 1) {
            _state.value = currentState.copy(page = currentState.page - 1)
            getPictures(choiceSearch, currentState.page)
        }
    }
}