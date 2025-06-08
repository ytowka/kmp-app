package com.example.api.content

import kotlinx.serialization.Serializable
@Serializable
data class ContentResponse(
    val id: Long,
    val themeId: Long,
    val name: String,
    val imageUrl: String?,
    val avgMark: Float?,
    val reviewCount: Int,
)