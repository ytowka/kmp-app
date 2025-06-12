package com.example.core.paging

data class PagingResponse<T>(
    val data: List<T>,
    val page: Int,
    val hasNextPage: Boolean,
) {
}