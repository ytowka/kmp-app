package com.example.feature.review.ui.edit

import com.example.feature.content.ui.ContentModel

sealed interface EditReviewIntent {
    data class EditMark(val mark: Int) : EditReviewIntent
    data class EditText(val text: String) : EditReviewIntent
    object Save : EditReviewIntent
    object Delete : EditReviewIntent
    object LoadReview : EditReviewIntent

    data class UpdateContent(val content: ContentModel) : EditReviewIntent
    data class UpdateMode(val mode: EditReviewMode) : EditReviewIntent
}

sealed interface EditReviewSideEffect {
    object ReviewUpdated : EditReviewSideEffect
}