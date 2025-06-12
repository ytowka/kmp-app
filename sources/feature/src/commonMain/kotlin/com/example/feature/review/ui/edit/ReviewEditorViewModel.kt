package com.example.feature.review.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlin.uuid.ExperimentalUuidApi

@KoinViewModel
class ReviewEditorViewModel(
    val editReviewUseCase: EditReviewUseCase,
    val writeReviewUseCase: WriteReviewUseCase,
    val getContentByIdUseCase: GetContentByIdUseCase,
    val deleteReviewUseCase: DeleteReviewUseCase,
    val getReviewByUserAndContentUseCase: GetReviewByUserAndContentUseCase,
    val getMeUseCase: GetMeUseCase,
    val contentId: Long
) : ViewModel() {

    val _uiState = MutableStateFlow(EditReviewState())
    val uiState: StateFlow<EditReviewState> = _uiState.asStateFlow()

    val updateFlow = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            getContentByIdUseCase(contentId).onSuccess { content ->
                _uiState.update { it.copy(content = content.toContentModel()) }
            }
        }
        getWrittenReview()
    }


    fun editMark(mark: Int){
        _uiState.update {
            it.copy(mark = mark)
        }
    }

    fun editText(text: String){
        _uiState.update {
            it.copy(text = text)
        }
    }

    fun delete(){
        val mode = uiState.value.mode
        if (mode is EditReviewMode.Edit){
            viewModelScope.launch {
                deleteReviewUseCase(mode.reviewModel.id).onSuccess {
                    updateFlow.emit(Unit)
                }
            }
        }
    }

    fun save() {
        if(uiState.value.isValid){
            viewModelScope.launch {
                val request = ReviewRequestDto(
                    contentId = contentId,
                    mark = uiState.value.mark ?: 0,
                    text = uiState.value.text
                )
                when(_uiState.value.mode){
                    is EditReviewMode.Edit -> editReviewUseCase(request)
                    EditReviewMode.New -> writeReviewUseCase(request)
                    EditReviewMode.Pending -> Result.failure(Exception("not valid"))
                }.onSuccess {
                    updateFlow.emit(Unit)
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
                    _uiState.update {
                        it.copy(
                            mode = EditReviewMode.Edit(reviewModel = review.toReviewModel().reviewModel),
                            text = review.text,
                            mark = review.mark
                        )
                    }
                }.onFailure {
                    _uiState.update {
                        it.copy(mode = EditReviewMode.New)
                    }
                }
        }

    }
}