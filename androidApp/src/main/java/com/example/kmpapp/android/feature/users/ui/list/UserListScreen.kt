package com.example.kmpapp.android.feature.users.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.kmpapp.android.coreui.SearchTopBar
import com.example.feature.users.ui.UserModel
import com.example.feature.users.ui.list.UserListViewModel
import com.example.kmpapp.android.coreui.injectViewModel
import com.example.kmpapp.android.coreui.rememberPageableListState
import org.koin.androidx.compose.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun UserListScreen(
    userListViewModel: UserListViewModel= injectViewModel(),
    onUserSelected: (UserModel) -> Unit,
) {
    val state by userListViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopBar(
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            text = state.searchQuery,
            onQueryChanged = { searchQuery -> userListViewModel.onQueryChange(searchQuery) },
            onActionClick = {}
        )

        val lazyListState = rememberPageableListState (
            state = state.currentPagingState,
            nextPageRequest = { userListViewModel.getNextPage() }
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            state = lazyListState
        ) {
            items(state.currentPagingState.list){ item: UserModel ->
                UserListItem(
                    userModel = item,
                    onClick = { onUserSelected(item) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun UserListItem(
    userModel: UserModel,
    onClick: () -> Unit,
){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val placeholder =  rememberVectorPainter(Icons.Filled.AccountCircle)
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(56.dp),
                model = userModel.avatarUrl,
                contentDescription = "user_avatar",
                contentScale = ContentScale.Crop,
                placeholder = placeholder,
                fallback = placeholder,
                error = placeholder
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "@${userModel.login}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = userModel.id.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}