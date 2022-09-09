package com.elephant.searchspacepictures.main_screen

import com.elephant.searchspacepictures.models.ResponseImages

data class MainScreenState(
    val loaded: Boolean = false,
    val page: Int = 1,
    val listResponseImages: List<ResponseImages> = emptyList()
)