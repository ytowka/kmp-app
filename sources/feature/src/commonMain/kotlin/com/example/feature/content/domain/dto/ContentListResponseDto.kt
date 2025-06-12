package com.example.feature.content.domain.dto

import com.example.api.content.ContentResponse

data class ContentListResponseDto(
    val content: List<ContentDto>,
    val page: Int,
    val hasNextPage: Boolean,
)