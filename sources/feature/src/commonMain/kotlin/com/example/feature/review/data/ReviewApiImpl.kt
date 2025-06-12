package com.example.feature.review.data

import com.example.api.review.ReviewApi
import com.example.api.review.ReviewListResponse
import com.example.api.review.ReviewRequest
import com.example.api.review.ReviewResponse
import com.example.network.bodyOrThrow
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [ReviewApi::class])
class ReviewApiImpl(
    private val httpClient: HttpClient
) : ReviewApi{

    override fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponse  = runBlocking{
        httpClient.get("/api/reviews/content/$contentId"){
            parameter("page", page)
        }.bodyOrThrow()
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getReviewsByUser(userId: Uuid, page: Int): ReviewListResponse = runBlocking {
        httpClient.get("/api/reviews/user/$userId"){
            parameter("page", page)
        }.bodyOrThrow()
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getReviewByUserContent(userId: Uuid, contentId: Long): ReviewResponse = runBlocking {
        httpClient.get("/api/reviews/user/$userId/content/$contentId").bodyOrThrow()
    }

    override fun writeReview(reviewRequest: ReviewRequest): Unit = runBlocking {
        httpClient.post("/api/reviews") {
            setBody(reviewRequest)
        }
    }

    override fun editReview(reviewRequest: ReviewRequest): Unit = runBlocking {
        httpClient.put("/api/reviews") {
            setBody(reviewRequest)
        }
    }

    override fun deleteReview(reviewId: Long): Unit = runBlocking {
        httpClient.delete("/api/reviews/$reviewId")
    }
}