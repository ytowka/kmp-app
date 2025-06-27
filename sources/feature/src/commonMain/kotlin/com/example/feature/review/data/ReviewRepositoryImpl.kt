package com.example.feature.review.data

import com.example.feature.review.domain.dto.ReviewDto
import com.example.feature.review.domain.dto.ReviewListResponseDto
import com.example.feature.review.domain.dto.ReviewRequestDto
import com.example.feature.review.domain.repository.ReviewRepository
import com.example.api.review.ReviewApi
import com.example.api.review.ReviewListResponse
import com.example.api.review.ReviewRequest
import com.example.api.review.ReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class ReviewRepositoryImpl(
    private val reviewApi: ReviewApi,
    @Named("baseUrl") private val baseUrl: String
) : ReviewRepository {

    private val updateFlow = MutableSharedFlow<Unit>()

    override suspend fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponseDto {
        return withContext(Dispatchers.IO) {
            reviewApi.getReviewsByContent(contentId, page).toDto(baseUrl)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getReviewsByUser(userId: Uuid, page: Int): ReviewListResponseDto {
        return withContext(Dispatchers.IO){
            reviewApi.getReviewsByUser(userId, page).toDto(baseUrl)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getReviewsByUserAndContent(userId: Uuid, contentId: Long): ReviewDto {
        return withContext(Dispatchers.IO){
            reviewApi.getReviewByUserContent(userId, contentId).toDto(baseUrl)
        }
    }

    override suspend fun writeReview(reviewRequest: ReviewRequestDto) {
        return withContext(Dispatchers.IO) {
            reviewApi.writeReview(reviewRequest.toApiRequest())
            updateFlow.emit(Unit)
        }
    }

    override suspend fun editReview(reviewRequest: ReviewRequestDto) {
        return withContext(Dispatchers.IO) {
            reviewApi.editReview(reviewRequest.toApiRequest())
            updateFlow.emit(Unit)
        }
    }

    override suspend fun deleteReview(reviewId: Long) {
        return withContext(Dispatchers.IO) {
            reviewApi.deleteReview(reviewId)
            updateFlow.emit(Unit)
        }
    }

    override fun subscribeReviewUpdate(): Flow<Unit> {
        return updateFlow
    }
}

fun ReviewResponse.toDto(
    baseUrl: String
): ReviewDto = ReviewDto(
    id = id,
    userId = this.userId,
    contentId = contentId,
    contentName = contentName,
    userAvatarUrl = baseUrl+userAvatarUrl,
    userName = userName,
    mark = mark,
    writeTime = writeTime,
    text = text
)

fun ReviewListResponse.toDto(
    baseUrl: String
): ReviewListResponseDto = ReviewListResponseDto(
    reviews = reviews.map { it.toDto(baseUrl) },
    page = page,
    hasNextPage = hasNextPage,
)

fun ReviewRequestDto.toApiRequest(): ReviewRequest = ReviewRequest(
    contentId = contentId,
    mark = mark,
    text = text,
)