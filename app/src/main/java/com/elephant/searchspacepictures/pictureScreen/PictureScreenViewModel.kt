package com.elephant.searchspacepictures.pictureScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elephant.data.repository.NasaApiImplementation
import com.elephant.domain.usecase.CaseResult
import com.elephant.domain.usecase.GetSearchQueryPictures
import com.elephant.searchspacepictures.models.ResponseUrlPictures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PictureScreenViewModel(private val picture: ResponseUrlPictures) : ViewModel() {
    private val searchRepository = NasaApiImplementation()
    private val getSearchQueryPictures = GetSearchQueryPictures(searchRepository)
    private val regexOrig = Regex("orig")

    private val _state = MutableLiveData(PictureScreenState())
    val state: MutableLiveData<PictureScreenState> = _state

    private val currentState: PictureScreenState
        get() = state.value!!


    suspend fun loadingPictures() {
        withContext(Dispatchers.IO) {
            getSearchQueryPictures.invoke(picture.originLinkForList).collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        if (result.response.isNotEmpty()) {
                            withContext(Dispatchers.Main) {
                                _state.value = currentState.copy(
                                    pictureUrl = result.response.first {
                                        regexOrig.containsMatchIn(it)
                                    }
                                )
                            }
                        }
                    }
                    is CaseResult.Failure -> {}
                    else -> {}
                }
            }
        }
    }
}