package com.example.kmpapp.android.feature.users.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.core.paging.PagingState
import com.example.kmpapp.android.coreui.TopBar
import com.example.feature.review.ui.ReviewCard
import com.example.feature.users.ui.info.UserInfoViewModel
import com.example.kmpapp.android.coreui.rememberPageableListState
import com.example.kmpapp.android.feature.review.ui.list.MarkBadge
import org.koin.androidx.compose.koinViewModel
import com.example.kmpapp.android.R
import com.example.kmpapp.android.coreui.injectViewModel

@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel= injectViewModel(),
    onBack: () -> Unit,
    onContentClick: (ReviewCard) -> Unit,
    onReviewClick: (ReviewCard) -> Unit,
){
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxSize()
    ) {
        TopBar(
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onActionClick = onBack,
            text = stringResource(R.string.user_profile)
        )

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                ))
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val placeholder = rememberVectorPainter(Icons.Filled.AccountCircle)
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    model = state.userModel?.avatarUrl,
                    contentDescription = "user_avatar",
                    contentScale = ContentScale.Crop,
                    placeholder = placeholder,
                    fallback = placeholder,
                    error = placeholder
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "@${state.userModel?.login ?: ""}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = state.userModel?.fullName.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        UserProfileReviewList(
            modifier = Modifier.weight(1f),
            pagingState = state.reviewListState,
            nextPageRequest = viewModel::getNextPage,
            onContentClick = onContentClick,
            onReviewClick = onReviewClick.takeIf { state.isMe },
        )
    }
}

@Composable
fun UserProfileReviewList(
    modifier: Modifier = Modifier,
    pagingState: PagingState<ReviewCard>,
    nextPageRequest: () -> Unit,
    onContentClick: (ReviewCard) -> Unit,
    onReviewClick: ((ReviewCard) -> Unit)?,
){

    val listState = rememberPageableListState(pagingState, nextPageRequest)

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pagingState.list, key = { "${it.reviewModel.contentId}${it.reviewUserInfo.userId}" }){
            UserProfileReviewCard(
                it,
                onContentClick = { onContentClick(it) },
                onCardClick = if(onReviewClick != null){
                    {
                        onReviewClick(it)
                    }}else null
            )
        }
        item {
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@Composable
fun UserProfileReviewCard(
    reviewCard: ReviewCard,
    onContentClick: () -> Unit,
    onCardClick: (() -> Unit)?,
){
    Card{
        Column(
            modifier = Modifier
                .then(if (onCardClick != null) Modifier.clickable(onClick = onCardClick) else Modifier)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val placeholder = rememberVectorPainter(Icons.Filled.AccountCircle)
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    model = reviewCard.reviewUserInfo.userAvatarUrl,
                    contentDescription = "user_avatar",
                    contentScale = ContentScale.Crop,
                    placeholder = placeholder,
                    fallback = placeholder,
                    error = placeholder
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "@${reviewCard.reviewUserInfo.userName}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    ClickableText(
                        modifier = Modifier.clickable(onClick = onContentClick),
                        text = buildAnnotatedString {
                            append(stringResource(R.string.writesAbout))
                            append(" ")
                            withStyle(style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                            )) {
                                append(reviewCard.reviewModel.contentName)
                            }
                        },
                        style = MaterialTheme.typography.titleMedium,
                        onClick = {
                            onContentClick()
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                MarkBadge(
                    mark = reviewCard.reviewModel.mark
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = reviewCard.reviewModel.text,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}