package com.example.feature.auth.data

expect class ImageResolver {

    fun resolveImage(uri: String): ImageResolverResult?
}

class ImageResolverResult(
    val image: ByteArray,
    val filename: String,
)