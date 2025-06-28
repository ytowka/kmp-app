package com.example.kmpapp.android.feature.topics.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.feature.content.ui.ContentModel
import com.example.feature.topics.ui.TopicModel
import com.example.feature.topics.ui.TopicViewModel
import com.example.kmpapp.android.coreui.TopBar
import com.example.kmpapp.android.feature.content.ui.RecomendedContentList
import org.koin.androidx.compose.koinViewModel
import com.example.kmpapp.android.R
import com.example.kmpapp.android.coreui.injectViewModel

@Composable
fun TopicScreen(
    topicsViewModel: TopicViewModel = injectViewModel(),
    onTopicClick: (TopicModel) -> Unit,
) {
    val state by topicsViewModel.state.collectAsState()

    Column {
        TopBar(
            actionIcon = null,
            onActionClick = {},
            text = stringResource(R.string.main),
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Header(stringResource(R.string.topics))
            }
            items(state.topics, key = { it.id }){
                TopicListItem(topic = it, onClick = {onTopicClick(it)})
            }
        }
    }

}


@Composable
fun Header(
    text: String,
){
    Column {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
        )
    }
}

@Composable
fun TopicListItem(topic: TopicModel, onClick: () -> Unit) {
    Card(
        onClick = onClick
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = topic.imageUrl,
                contentDescription = null
            )
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = topic.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = topic.contentCount.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}