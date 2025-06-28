package com.example.kmpapp.android.feature.content.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.feature.content.ui.ContentListIntent
import com.example.kmpapp.android.coreui.SearchTopBar
import com.example.feature.content.ui.ContentListViewModel
import com.example.feature.content.ui.ContentModel
import com.example.kmpapp.android.coreui.rememberPageableListState
import com.example.kmpapp.android.feature.topics.ui.Header
import com.example.kmpapp.android.R

@Composable
fun ContentListScreen(
    viewModel: ContentListViewModel,
    onContentClick: (ContentModel) -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val pagingListState = rememberPageableListState(
        state = state.currentPagerState,
        nextPageRequest = { viewModel.accept(ContentListIntent.LoadNext) }
    )

    Column {
        SearchTopBar(
            actionIcon = Icons.Filled.ArrowBack,
            onActionClick = onBack,
            text = state.searchQuery,
            onQueryChanged = {
                viewModel.accept(ContentListIntent.Search(it))
            }
        )

        LazyColumn(
            state = pagingListState,
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Header(stringResource(R.string.topic, state.topicName))
            }
            items(state.currentPagerState.list, key = { it.id }) {
                ContentListItem(
                    contentModel = it,
                    onClick = { onContentClick(it) }
                )
            }
        }
    }
}

@Composable
fun ContentListItem(
    contentModel: ContentModel,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .wrapContentHeight()
                .heightIn(min = 200.dp)
                .fillMaxWidth(),
            model = contentModel.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
                .fillMaxWidth()
                .height(50.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            text = contentModel.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
        )

        contentModel.avgMark?.let { avgMark ->
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .background(color = MarkColors.getMarkColor(avgMark), shape = CircleShape)
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
                text = ContentUtils.formatMark(avgMark),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                ),
            )
        }
    }

}

