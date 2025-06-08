package com.example.api.review

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse (
    val id: Long,
    val userId: String,
    val contentId: Long,
    val contentName: String,
    val userAvatarUrl: String?,
    val userName: String,
    val mark: Int,
    val writeTime: Long,
    val text: String
)