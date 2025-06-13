package com.example.feature.auth.data

import org.koin.core.annotation.Single

expect class ImageResolver {

    fun resolveImage(uri: String): ImageResolverResult?
}

class ImageResolverResult(
    val image: ByteArray,
    val filename: String,
)