package com.example.feature.content.data

import com.example.feature.content.domain.dto.ContentDto
import com.example.feature.content.domain.dto.ContentListResponseDto
import com.example.feature.content.domain.repository.ContentRepository
import com.example.api.content.ContentApi
import com.example.api.content.ContentListResponse
import com.example.api.content.ContentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single(
    binds = [ContentRepository::class],
)
class ContentRepositoryImpl(
    private val contentApi: ContentApi,
    @Named("baseUrl") private val baseUrl: String
) : ContentRepository {
    override suspend fun getAllContent(topicId: Long, page: Int): ContentListResponseDto = withContext(Dispatchers.IO){
        contentApi.getAllContents(topicId, page).toDto(baseUrl)
    }

    override suspend fun getById(contentId: Long): ContentDto = withContext(Dispatchers.IO){
        contentApi.getById(contentId).toDto(baseUrl)
    }

    override suspend fun searchContent(topicId: Long, query: String): List<ContentDto>  = withContext(Dispatchers.IO){
        contentApi.search(topicId, query).map {
            it.toDto(baseUrl = baseUrl)
        }
    }

    override suspend fun getRecommendedContent(): List<ContentDto> = withContext(Dispatchers.IO){
        contentApi.getRecommendedContent().map {
            it.toDto(baseUrl = baseUrl)
        }
    }

    override suspend fun getRecommendedContent(topicId: Long): List<ContentDto> = withContext(Dispatchers.IO){
        contentApi.getRecommendedContent(topicId).map {
            it.toDto(baseUrl = baseUrl)
        }
    }

}

fun ContentResponse.toDto(
    baseUrl: String
): ContentDto = ContentDto(
    id = this.id,
    themeId = this.themeId,
    name = this.name,
    imageUrl = baseUrl+this.imageUrl,
    avgMark = this.avgMark,
    reviewCount = reviewCount,
)

fun ContentListResponse.toDto(
    baseUrl: String
): ContentListResponseDto = ContentListResponseDto(
    content = content.map {
        it.toDto(baseUrl = baseUrl)
    },
    page = page,
    hasNextPage = hasNextPage
)