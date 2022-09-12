package com.elephant.searchspacepictures.main_screen

import com.elephant.searchspacepictures.models.ResponseUrlPictures

data class MainScreenState(
    val loaded: Boolean = false,
    val page: Int = 1,
    val listResponsePicture: List<ResponseUrlPictures> = emptyList()
)