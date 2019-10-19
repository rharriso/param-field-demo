package com.rharriso

data class Image(val title: String, val url: String)

enum class ImageSize { original, small, }

class ImageQueryService {
    fun fetchImage(imageSize: ImageSize?): Image {
        val imageSizeUsed = imageSize ?: ImageSize.original

        return Image(
                title = "Zugspitze at Sunrise",
                url = "static/zugspitze-sunrise-$imageSizeUsed.jpg"
        )
    }
}