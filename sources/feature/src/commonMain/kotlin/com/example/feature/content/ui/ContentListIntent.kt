package com.example.feature.content.ui

import com.example.core.paging.PagingState

sealed interface ContentListIntent {

    data class Search(
        val query: String
    ) : ContentListIntent

    data class UpdateList(
        val newListData: List<ContentModel>
    ) : ContentListIntent

    data object LoadNext : ContentListIntent

    data class UpdatePager(
        val pagerState: PagingState<ContentModel>
    ) : ContentListIntent
}

sealed interface ContentListSideEffect {

}