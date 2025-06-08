package com.example.api.review

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ReviewApi {

    fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponse
    fun getReviewsByUser(userId: Uuid, page: Int): ReviewListResponse
    fun getReviewByUserContent(userId: Uuid, contentId: Long) : ReviewResponse
    fun writeReview(reviewRequest: ReviewRequest)
    fun editReview(reviewRequest: ReviewRequest)
    fun deleteReview(reviewId: Long)
}