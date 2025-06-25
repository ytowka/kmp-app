package com.example.feature.auth.data


import kotlinx.cinterop.*
import platform.Foundation.*
import org.koin.core.annotation.Single
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.reinterpret
import platform.posix.memcpy

@Single
actual class ImageResolver {

    @OptIn(ExperimentalForeignApi::class)
    actual fun resolveImage(uri: String): ImageResolverResult? {
        val url = NSURL(string = uri)
        val data = NSData.dataWithContentsOfURL(url) ?: return null

        val size = data.length.toInt()
        val byteArray = ByteArray(size)

        data.bytes?.let { pointer ->
            byteArray.usePinned { pinned ->
                memcpy(pinned.addressOf(0), pointer, size.convert())
            }
        } ?: return null

        val filename = url.lastPathComponent ?: "avatar.jpg"

        return ImageResolverResult(image = byteArray, filename = filename)
    }
}

//actual class ImageResolver {
//
//    actual fun resolveImage(uri: String): ImageResolverResult? {
//        TODO("Not yet implemented")
//    }
//
//}