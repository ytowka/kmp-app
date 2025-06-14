package com.example.feature.auth.ui

import androidx.lifecycle.viewModelScope
import com.example.core.arch.MviViewModel
import com.example.feature.auth.domain.dto.LoginRequestDto
import com.example.feature.auth.domain.dto.RegisterRequestDto
import com.example.feature.auth.domain.usecase.LoginUseCase
import com.example.feature.auth.domain.usecase.RegisterUseCase
import com.example.network.ApiException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : MviViewModel<AuthIntent, AuthState, AuthSideEffect>(){


    override val initialState: AuthState
        get() = AuthState()

    override fun reduce(state: AuthState, intent: AuthIntent): AuthState {
        return when(intent){
            AuthIntent.OnCreateAccount -> state.copy(
                currentForm = Form.REGISTER,
                registerState = state.registerState.copy(error = null),
                loginState = state.loginState.copy(error = null),
            )
            is AuthIntent.OnEmailChange -> state.copy(
                registerState = state.registerState.copy(email = intent.email),
            )
            is AuthIntent.OnFullNameChange -> state.copy(
                registerState = state.registerState.copy(fullName = intent.fullName),
            )
            is AuthIntent.OnImagePicked -> state.copy(registerState = state.registerState.copy(imageUrl = intent.uri))
            is AuthIntent.OnPasswordChange -> state.copy(
                loginState = state.loginState.copy(password = intent.password),
                registerState = state.registerState.copy(password = intent.password)
            )
            is AuthIntent.OnPasswordConfirmationChange -> state.copy(
                registerState = state.registerState.copy(passwordConfirmation = intent.passwordConfirmation),
            )
            is AuthIntent.OnPhoneChange -> state.copy(registerState = state.registerState.copy(phone = intent.phone))
            is AuthIntent.OnUsernameChange -> state.copy(
                loginState = state.loginState.copy(username = intent.username),
                registerState = state.registerState.copy(username = intent.username)
            )
            AuthIntent.ReturnToSignIn -> state.copy(
                currentForm = Form.LOGIN,
                registerState = state.registerState.copy(error = null),
                loginState = state.loginState.copy(error = null),
            )
            is AuthIntent.ShowError -> state.copy(
                loginState = state.loginState.copy(error = intent.error),
                registerState = state.registerState.copy(error = intent.error)
            )
            else -> state
        }
    }

    override fun postProcess(oldState: AuthState, newState: AuthState, intent: AuthIntent) {
        when(intent){
            AuthIntent.OnRegister -> register(newState.registerState)
            AuthIntent.OnLogin -> login(newState.loginState)
            else -> {}
        }
    }

    private fun register(registerState: RegisterState){
        viewModelScope.launch {
            if(registerState.isValid){
                registerUseCase(
                    RegisterRequestDto(
                        username = registerState.username,
                        password = registerState.password,
                        email = registerState.email,
                        fullName = registerState.fullName,
                        phone = registerState.phone,
                        avatarUri = registerState.imageUrl,
                    )
                ).onSuccess {
                    showSideEffect(AuthSideEffect.Authenticated)
                }.onFailure { throwable ->
                    val error = mapError(throwable)
                    accept(AuthIntent.ShowError(error))
                }
            } else {
                accept(AuthIntent.ShowError(Error.FIELD))
            }
        }
    }


    private fun login(loginState: LoginState) {
        viewModelScope.launch {
            if(loginState.isValid){
                loginUseCase(
                    LoginRequestDto(
                        username = loginState.username,
                        password = loginState.password,
                    )
                ).onSuccess {
                    showSideEffect(AuthSideEffect.Authenticated)
                }.onFailure { throwable ->
                    val error = mapError(throwable)
                    accept(AuthIntent.ShowError(error))
                }

            }else{
                accept(AuthIntent.ShowError(Error.FIELD))
            }
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