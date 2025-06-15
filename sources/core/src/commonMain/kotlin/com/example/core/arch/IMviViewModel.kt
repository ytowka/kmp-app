package com.example.core.arch

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IMviViewModel<Intent, State, SideEffect> {

    val state: StateFlow<State>
    val sideEffect: SharedFlow<SideEffect>
    fun accept(intent: Intent)
}