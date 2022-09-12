package com.elephant.searchspacepictures.main_screen

import com.elephant.searchspacepictures.models.ResponseUrlPictures

data class MainScreenState(
    val loaded: Boolean = false,
    val page: Int = 1,
    val totalPictures: Long = 0L,
    val listResponsePicture: List<ResponseUrlPictures> = emptyList(),
    val notResponse:Boolean = false
)