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

abstract class MviViewModel<Intent, State, SideEffect> : ViewModel(), IMviViewModel<Intent, State, SideEffect> {

    abstract val initialState: State

    open val debugMode: Boolean = DEBUG

    private val _state: MutableStateFlow<State> by lazy {
        MutableStateFlow(initialState)
    }
    override val state: StateFlow<State>
        get() = _state

    private val _sideEffect = MutableSharedFlow<SideEffect>(extraBufferCapacity = 5)
    override val sideEffect: SharedFlow<SideEffect>
        get() = _sideEffect


    private val _intent = MutableSharedFlow<Intent>(extraBufferCapacity = 5)

    init {
        viewModelScope.launch {
            launch {
                _intent.collect(::processIntent)
            }
            launch(Dispatchers.Main) {
                log("loadData")
                loadData()
            }
        }
    }

    private fun processIntent(intent: Intent) {
        log("processIntent $intent")
        _state.update { currentState ->
            val newState = reduce(currentState, intent)
            log("reduced\n\t-$currentState\n\t+$newState")
            postProcess(currentState, newState, intent)
            newState
        }
    }

    override fun accept(intent: Intent) {
        _intent.tryEmit(intent)
    }

    fun showSideEffect(sideEffect: SideEffect) {
        log("showSideEffect $sideEffect")
        _sideEffect.tryEmit(sideEffect)
    }

    open suspend fun loadData() {}

    abstract fun reduce(state: State, intent: Intent): State

    open fun postProcess(oldState: State, newState: State, intent: Intent) {  }

    private fun log(message: String){
        if(debugMode) Napier.d("${this::class.simpleName}: $message", tag = MVI_TAG)
    }

    companion object {
        private const val DEBUG: Boolean = true
    }
}