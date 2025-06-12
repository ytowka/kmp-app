package com.example.feature.review.ui.edit

import com.example.feature.content.ui.ContentModel
import com.example.feature.review.ui.ReviewModel

data class EditReviewState(
    val mode: EditReviewMode = EditReviewMode.Pending,
    val mark: Int? = null,
    val text: String = "",
    val content: ContentModel? = null
){
    val isValid: Boolean
        get() = when(mode){
            is EditReviewMode.Edit -> {
                (mark != mode.reviewModel.mark ||
                        text != mode.reviewModel.text) && mark != null
            }
            EditReviewMode.New -> {
                mark != null
            }
            EditReviewMode.Pending -> false
        }
}

sealed interface EditReviewMode {
    data object Pending : EditReviewMode
    data object New : EditReviewMode
    class Edit(val reviewModel: ReviewModel) : EditReviewMode
}