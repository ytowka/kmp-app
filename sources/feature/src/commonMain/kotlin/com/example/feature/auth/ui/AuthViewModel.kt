package com.example.feature.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.auth.domain.dto.LoginRequestDto
import com.example.feature.auth.domain.dto.RegisterRequestDto
import com.example.feature.auth.domain.usecase.LoginUseCase
import com.example.feature.auth.domain.usecase.RegisterUseCase
import com.example.network.ApiException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory


@Factory
class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel(), RegisterFormCallback, LoginFormCallback {

    private val _uiState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState

    val successLogin: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun onRegister() {
        uiState.value.registerState.let { state ->
            if(state.isValid){
                viewModelScope.launch {
                    registerUseCase(
                        RegisterRequestDto(
                            username = state.username,
                            password = state.password,
                            email = state.email,
                            fullName = state.fullName,
                            phone = state.phone,
                            avatarUri = state.imageUrl,
                        )
                    ).onSuccess {
                        successLogin.value = true
                    }.onFailure { throwable ->
                        val error = mapError(throwable)
                        _uiState.update {
                            it.copy(registerState = it.registerState.copy(error = error))
                        }

                    }
                }
            }else{
                _uiState.update { uistate ->
                    uistate.copy(registerState = state.copy(showFieldError = true, error = Error.FIELD))
                }
            }
        }
    }

    override fun returnToSignIn() {
        _uiState.update {
            it.copy(currentForm = Form.LOGIN)
        }
    }


    override fun onLogin() {
        uiState.value.loginState.let {
            if(it.isValid){
                viewModelScope.launch {
                    loginUseCase(
                        LoginRequestDto(
                            username = it.username,
                            password = it.password,
                        )
                    ).onSuccess {
                        successLogin.value = true
                    }.onFailure { throwable ->
                        val error = mapError(throwable)
                        _uiState.update { uistate ->
                            uistate.copy(loginState = uistate.loginState.copy(error = error))
                        }
                    }
                }
            }else{
                _uiState.update { state ->
                    state.copy(loginState = it.copy(showFieldError = true, error = Error.FIELD))
                }
            }
        }
    }

    override fun onCreateAccount() {
        _uiState.update {
            it.copy(currentForm = Form.REGISTER)
        }
    }

    override fun onUsernameChange(username: String) {
        _uiState.update {
            it.copy(
                loginState = it.loginState.copy(username = username),
                registerState = it.registerState.copy(username = username)
            )
        }
    }

    override fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                loginState = it.loginState.copy(password = password),
                registerState = it.registerState.copy(password = password)
            )
        }
    }


    override fun onFullNameChange(fullName: String) {
        _uiState.update {
            it.copy(
                registerState = it.registerState.copy(fullName = fullName),
            )
        }
    }

    override fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                registerState = it.registerState.copy(email = email),
            )
        }
    }

    override fun onPhoneChange(phone: String) {
        _uiState.update {
            it.copy(registerState = it.registerState.copy(phone = phone))
        }
    }

    override fun onImagePicked(uri: String?) {
        _uiState.update {
            it.copy(registerState = it.registerState.copy(imageUrl = uri))
        }
    }

    override fun onPasswordConfirmationChange(passwordConfirmation: String) {
        _uiState.update {
            it.copy(
                registerState = it.registerState.copy(passwordConfirmation = passwordConfirmation),
            )
        }
    }


    private fun mapError(throwable: Throwable): Error{
        return when(throwable){
            is ApiException -> {
                when(throwable.exceptionBody.status) {
                    HttpStatusCode.BadRequest.value -> Error.WRONG_CREDENTIALS
                    else -> Error.OTHER
                }
            }
            else -> Error.OTHER
        }
    }
}