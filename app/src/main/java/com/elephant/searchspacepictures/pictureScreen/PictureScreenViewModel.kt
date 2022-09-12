package com.elephant.searchspacepictures.pictureScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elephant.searchspacepictures.models.ResponseUrlPictures

class PictureScreenViewModel(private val picture: ResponseUrlPictures) : ViewModel() {

    private val _state = MutableLiveData(PictureScreenState())
    val state: LiveData<PictureScreenState> = _state

    private val currentState: PictureScreenState
        get() = state.value!!

}