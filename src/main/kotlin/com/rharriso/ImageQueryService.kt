package com.rharriso

data class Image(val title: String, val url: String)

class ImageQueryService {
    fun fetchImage(imageSize: String?): Image {
        val imageSizeUsed = imageSize ?: "original"

        return Image(
                title = "Zugspitze at Sunrise",
                url = "static/zugspitze-sunrise-$imageSizeUsed.jpg"
        )
    }
}