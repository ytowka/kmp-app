package com.example.feature.topics.data

import com.example.feature.topics.domain.dto.TopicDto
import com.example.feature.topics.domain.repository.TopicRepository
import com.example.api.theme.ThemeApi
import com.example.api.theme.ThemeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single(binds = [TopicRepository::class])
class TopicRepositoryImpl(
    private val themeApi: ThemeApi,
    @Named("baseUrl") private val baseUrl: String
) : TopicRepository {
    override suspend fun getTopics(): List<TopicDto> {
        return withContext(Dispatchers.IO){
            themeApi.getAllThemes().map { it.toDto(baseUrl) }
        }
    }
}

private fun ThemeResponse.toDto(
    baseUrl: String
): TopicDto = TopicDto(
    id = id,
    name = name,
    contentCount = contentCount,
    imageUrl = baseUrl + imageUrl,
)