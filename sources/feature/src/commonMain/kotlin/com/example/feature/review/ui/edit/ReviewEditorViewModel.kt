package com.example.feature.review.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.arch.MviViewModel
import com.example.feature.content.domain.usecase.GetContentByIdUseCase
import com.example.feature.content.ui.toContentModel
import com.example.feature.review.domain.dto.ReviewRequestDto
import com.example.feature.review.domain.usecase.DeleteReviewUseCase
import com.example.feature.review.domain.usecase.EditReviewUseCase
import com.example.feature.review.domain.usecase.GetReviewByUserAndContentUseCase
import com.example.feature.review.domain.usecase.WriteReviewUseCase
import com.example.feature.review.ui.toReviewModel
import com.example.feature.users.domain.usecase.GetMeUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi

@Factory
class ReviewEditorViewModel(
    val editReviewUseCase: EditReviewUseCase,
    val writeReviewUseCase: WriteReviewUseCase,
    val getContentByIdUseCase: GetContentByIdUseCase,
    val deleteReviewUseCase: DeleteReviewUseCase,
    val getReviewByUserAndContentUseCase: GetReviewByUserAndContentUseCase,
    val getMeUseCase: GetMeUseCase,
    val contentId: Long
) : MviViewModel<EditReviewIntent, EditReviewState, EditReviewSideEffect>() {

    override val initialState: EditReviewState = EditReviewState()

    override suspend fun loadData() {
        getContentByIdUseCase(contentId).onSuccess { content ->
            accept(EditReviewIntent.UpdateContent(content.toContentModel()))
        }
        getWrittenReview()
    }

    override fun reduce(state: EditReviewState, intent: EditReviewIntent): EditReviewState {
        return when(intent){
            is EditReviewIntent.Delete -> state.copy(mode = EditReviewMode.Pending)
            is EditReviewIntent.EditMark -> state.copy(mark = intent.mark)
            is EditReviewIntent.EditText -> state.copy(text = intent.text)
            EditReviewIntent.LoadReview -> state.copy(mode = EditReviewMode.Pending)
            EditReviewIntent.Save -> state.copy(mode = EditReviewMode.Pending)
            is EditReviewIntent.UpdateContent -> state.copy(content = intent.content)
            is EditReviewIntent.UpdateMode -> {
                val writtenReview = (state.mode as? EditReviewMode.Edit)?.reviewModel
                state.copy(
                    mode = intent.mode,
                    mark = writtenReview?.mark,
                    text = writtenReview?.text.orEmpty()
                )
            }
        }
    }

    override fun postProcess(oldState: EditReviewState, newState: EditReviewState, intent: EditReviewIntent) {
        when(intent){
            EditReviewIntent.Delete -> delete(newState)
            EditReviewIntent.Save -> save(newState)
            else -> {}
        }
    }

    private fun delete(state: EditReviewState){
        val mode = state.mode
        if (mode is EditReviewMode.Edit){
            viewModelScope.launch {
                deleteReviewUseCase(mode.reviewModel.id).onSuccess {
                    showSideEffect(EditReviewSideEffect.ReviewUpdated)
                }
            }
        }
    }

    fun save(state: EditReviewState) {
        if(state.isValid){
            viewModelScope.launch {
                val request = ReviewRequestDto(
                    contentId = contentId,
                    mark = state.mark ?: 0,
                    text = state.text
                )
                when(state.mode){
                    is EditReviewMode.Edit -> editReviewUseCase(request)
                    EditReviewMode.New -> writeReviewUseCase(request)
                    EditReviewMode.Pending -> Result.failure(Exception("not valid"))
                }.onSuccess {
                    showSideEffect(EditReviewSideEffect.ReviewUpdated)
                }
            }

        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun getWrittenReview(){
        viewModelScope.launch {
            val me = getMeUseCase().getOrElse { return@launch }
            val params = GetReviewByUserAndContentUseCase.Params(
                userId = me.id,
                contentId = contentId
            )
            getReviewByUserAndContentUseCase(params)
                .onSuccess { review ->
                    accept(EditReviewIntent.UpdateMode(EditReviewMode.Edit(review.toReviewModel().reviewModel)))
                }.onFailure {
                    accept(EditReviewIntent.UpdateMode(EditReviewMode.New))
                }
        }
    }
}