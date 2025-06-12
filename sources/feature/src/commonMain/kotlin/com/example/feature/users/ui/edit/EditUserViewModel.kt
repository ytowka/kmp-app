package com.example.feature.users.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.usecase.GetMeUseCase
import com.example.feature.users.domain.usecase.GetUserByIdUseCase
import com.example.feature.users.domain.usecase.UpdateUserUseCase
import com.example.feature.users.ui.UserRoleModel
import com.example.feature.users.ui.toDto
import com.example.feature.users.ui.toUserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@KoinViewModel
class EditUserViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val userId: String,
) : ViewModel(), EditUserCallback {

    val _uiState = MutableStateFlow(EditUserState())
    val uiState: StateFlow<EditUserState> = _uiState

    init {
        viewModelScope.launch {
            getUserByIdUseCase.invoke(userId).onSuccess { result ->
                val me = getMeUseCase().getOrThrow()
                _uiState.update {
                    val user = result.toUserModel()

                    it.copy(
                        initalUser = user,
                        fullName = user.fullName,
                        email = user.email,
                        phone = user.phone,
                        role = user.role,
                        isBlocked = user.isBlocked,
                        isMe = me.id == user.id
                    )
                }
            }
        }
    }


    override fun changeFullName(fullName: String) {
        _uiState.update {
            it.copy(fullName = fullName)
        }
    }

    override fun changeEmail(email: String) {
       _uiState.update {
           it.copy(email = email)
       }
    }

    override fun changePhoneNumber(phoneNumber: String) {
        _uiState.update {
            it.copy(phone = phoneNumber)
        }
    }

    override fun changeRole(role: UserRoleModel) {
        _uiState.update {
            it.copy(role = role)
        }
    }

    override fun changeBlocked(blocked: Boolean) {
        _uiState.update {
            it.copy(isBlocked = blocked)
        }
    }

    override fun onSave() {
        _uiState.value.let { state ->
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
                    _uiState.update {
                        it.copy(userSaved = true)
                    }
                }
            }
        }
    }

    companion object {
        const val USER_ID_ARG = "USER_ID_ARG"
    }
}