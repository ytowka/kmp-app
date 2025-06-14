package com.example.core.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MVI_TAG = "MVI"

abstract class MviViewModel<Intent, State, SideEffect> : ViewModel() {

    abstract val initialState: State

    open val debugMode: Boolean = false

    private val _state: MutableStateFlow<State> by lazy {
        MutableStateFlow(initialState)
    }
    val state: StateFlow<State>
        get() = _state

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect>
        get() = _sideEffect


    private val _intent = MutableSharedFlow<Intent>(extraBufferCapacity = 5)

    init {
        viewModelScope.launch {
            launch {
                _intent.collect(::processIntent)
            }
            launch(Dispatchers.Main) {
                if(debugMode) Napier.d("loadData", tag = MVI_TAG)
                loadData()
            }
        }
    }

    private fun processIntent(intent: Intent) {
        if(debugMode) Napier.d("processIntent $intent", tag = MVI_TAG)
        _state.update { currentState ->
            val newState = reduce(currentState, intent)
            if(debugMode) Napier.d("reduced\n--$currentState\n++$newState", tag = MVI_TAG)
            postProcess(currentState, newState, intent)
            newState
        }
    }

    fun accept(intent: Intent) {
        if(debugMode) Napier.d("accept $intent", tag = MVI_TAG)
        _intent.tryEmit(intent)
    }

    fun showSideEffect(sideEffect: SideEffect) {
        if(debugMode) Napier.d("showSideEffect $sideEffect", tag = MVI_TAG)
        _sideEffect.tryEmit(sideEffect)
    }

    open suspend fun loadData() {}

    abstract fun reduce(state: State, intent: Intent): State

    open fun postProcess(oldState: State, newState: State, intent: Intent) {  }
}