package com.example.api.theme

import kotlinx.serialization.Serializable

@Serializable
data class ThemeResponse(
    val id: Long,
    val name: String,
    val contentCount: Int,
    val imageUrl: String,
)