package com.elephant.searchspacepictures.pictureScreen

import com.elephant.searchspacepictures.models.ResponseUrlPictures

data class PictureScreenState(
    val picture: ResponseUrlPictures = ResponseUrlPictures("", "")
)