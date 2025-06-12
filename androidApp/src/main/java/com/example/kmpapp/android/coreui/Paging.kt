package com.example.kmpapp.android.coreui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import com.example.core.paging.PagingState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull

private const val LOADING_TRIGGER_THRESHOLD = 2

@Composable
fun <T> rememberPageableListState(
    state: PagingState<T>,
    nextPageRequest: () -> Unit,
): LazyListState {
    val listState = rememberLazyListState()

    LaunchedEffect(state.hasNextPage){
        if(state.hasNextPage){
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                .mapNotNull { it.lastOrNull()?.index }
                .collectLatest {
                    if(it > state.list.size - LOADING_TRIGGER_THRESHOLD){
                        nextPageRequest()
                    }
                }
        }
    }

    return listState
}