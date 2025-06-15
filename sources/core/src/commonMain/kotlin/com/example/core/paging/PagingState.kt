package com.example.core.paging

import kotlinx.coroutines.flow.*

data class PagingState<T> (
    val list: List<T>,
    val currentPage: Int = -1,
    val hasNextPage: Boolean = true,
    val loadingPage: Int? = null,
){
     fun loadNext(source: suspend (page: Int) -> PagingResponse<T>): Flow<PagingState<T>> {
        if (loadingPage != null || !hasNextPage) return emptyFlow()
        return flow {
            val loadingState = PagingState(
                list = list,
                currentPage = currentPage,
                hasNextPage = hasNextPage,
                loadingPage = currentPage + 1
            )
            emit(loadingState)
            val updatedState = try {
                val data = source.invoke(currentPage + 1)
                PagingState(
                    list = list.plus(data.data),
                    currentPage = data.page,
                    hasNextPage = data.hasNextPage,
                    loadingPage = null
                )
            } catch (e: Exception) {
                PagingState(
                    list,
                    currentPage,
                    false,
                    null
                )
            }
            emit(updatedState)
        }
    }

    companion object {
        fun <T> initial(): PagingState<T> = PagingState(
            list = emptyList(),
            currentPage = -1,
            hasNextPage = true,
            loadingPage = null,
        )

        fun <T> static(list: List<T>): PagingState<T> = PagingState(
            list = list,
            currentPage = 0,
            hasNextPage = false,
            loadingPage = null,
        )
    }
}