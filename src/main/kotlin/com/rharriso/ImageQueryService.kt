package com.rharriso

data class Image(val title: String, val url: String)

class ImageQueryService {
    fun fetchImage() = Image(title = "Zugspitze at Sunrise", url = "static/zugspitze-sunrise-original.jpg")
}