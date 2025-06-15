package com.example.feature.users.ui.edit

import androidx.lifecycle.viewModelScope
import com.example.core.arch.MviViewModel
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.usecase.GetMeUseCase
import com.example.feature.users.domain.usecase.GetUserByIdUseCase
import com.example.feature.users.domain.usecase.UpdateUserUseCase
import com.example.feature.users.ui.toDto
import com.example.feature.users.ui.toUserModel
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Factory
class EditUserViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val userId: String,
) : MviViewModel<EditUserIntent, EditUserState, EditUserSideEffect>() {

    override val initialState: EditUserState = EditUserState()

    override suspend fun loadData() {
        getUserByIdUseCase.invoke(userId).onSuccess { result ->
            val me = getMeUseCase().getOrThrow()
            accept(EditUserIntent.LoadUser(
                userModel = result.toUserModel(),
                isMe = me.id == result.id
            ))
        }
    }

    override fun reduce(state: EditUserState, intent: EditUserIntent): EditUserState {
        return when(intent){
            is EditUserIntent.ChangeBlocked -> state.copy(isBlocked = intent.blocked)
            is EditUserIntent.ChangeEmail -> state.copy(email = intent.email)
            is EditUserIntent.ChangeFullName -> state.copy(fullName = intent.fullName)
            is EditUserIntent.ChangePhoneNumber -> state.copy(phone = intent.phoneNumber)
            is EditUserIntent.ChangeRole -> state.copy(role = intent.role)
            is EditUserIntent.LoadUser -> state.copy(
                initalUser = intent.userModel,
                fullName = intent.userModel.fullName,
                email = intent.userModel.email,
                phone = intent.userModel.phone,
                role = intent.userModel.role,
                isBlocked = intent.userModel.isBlocked,
                isMe = intent.isMe
            )
            EditUserIntent.Save -> state.copy(userSaved = true)
        }
    }

    override fun postProcess(oldState: EditUserState, newState: EditUserState, intent: EditUserIntent) {
        when(intent){
            EditUserIntent.Save -> onSave(newState)
            else -> { }
        }
    }

    private fun onSave(state: EditUserState) {
        if(state.isChanged && state.initalUser != null){
            viewModelScope.launch {
                val userDto = UserDto(
                    id = state.initalUser.id,
                    fullName = state.fullName,
                    email = state.email,
                    phone = state.phone,
                    isBlocked = state.isBlocked,
                    avatarUrl = state.initalUser.avatarUrl,
                    role = state.role.toDto(),
                    login = state.initalUser.login
                )
                updateUserUseCase.invoke(userDto)
                showSideEffect(EditUserSideEffect.UserSaved)
            }
        }
    }
}