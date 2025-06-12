package com.example.feature.auth.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

actual class ImageResolver(
    val contentResolver: ContentResolver
) {


    actual fun resolveImage(uri: String): ImageResolverResult? {
        val avatarUri = Uri.parse(uri)
        val name = contentResolver.query(avatarUri, null, null, null, null, null)?.use {
            val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(nameIndex)
        }
        val image = contentResolver.openInputStream(avatarUri)?.use { it.readBytes() }
        return if(name != null && image != null){
            return ImageResolverResult(
                image = image,
                filename = name
            )
        }else{
            null
        }
    }

}