package com.example.kmpapp.android.feature.content.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.feature.content.ui.ContentModel
import com.example.kmpapp.android.feature.review.ui.list.MarkBadge
import com.example.kmpapp.android.R

@Composable
fun RecomendedContentList(
    items: List<ContentModel>,
    onClick: (ContentModel) -> Unit
){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items, key = { it.id }) { content ->
            RecomendationListItem(
                contentModel = content,
                onClick = { onClick(content) }
            )
        }
    }
}



@Composable
fun RecomendationListItem(
    contentModel: ContentModel,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .width(100.dp)
            .fillMaxHeight()
    ){
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = contentModel.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .align(Alignment.BottomCenter),
            text = contentModel.name,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
        )

        MarkBadge(
            modifier = Modifier.align(Alignment.TopEnd),
            mark = contentModel.avgMark
        )
    }
}