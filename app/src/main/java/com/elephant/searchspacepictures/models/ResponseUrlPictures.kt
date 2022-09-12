package com.elephant.searchspacepictures.models

import java.io.Serializable

data class ResponseUrlPictures(val previewImage: String = "", val originLinkForList: String = "") :
    Serializable