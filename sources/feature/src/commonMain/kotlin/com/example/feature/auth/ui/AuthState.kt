package com.example.feature.auth.ui

import com.example.validation.FieldValidation

data class AuthState(
    val loginState: LoginState = LoginState(),
    val registerState: RegisterState = RegisterState(),
    val currentForm: Form = Form.LOGIN
)

enum class Form { LOGIN, REGISTER }

data class LoginState(
    val username: String = "",
    val password: String = "",
    val showFieldError: Boolean = false,
    val error: Error? = null,
){

    val isUsernameValid: Boolean by lazy {
            username.isNotBlank()
        }

    val isPasswordValid: Boolean by lazy {
        password.isNotBlank()
    }

    val isValid: Boolean by lazy {
        isUsernameValid && isPasswordValid
    }
}

data class RegisterState(
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val username: String = "",
    val imageUrl: String? = null,
    val passwordConfirmation: String = "",
    val fullName: String = "",
    val error: Error? = null,
    val showFieldError: Boolean = false,
){

    val isEmailValid: Boolean by lazy {
        FieldValidation.isEmailValid(email)
    }

    val isPasswordValid: Boolean by lazy {
        FieldValidation.isPasswordValid(password)
    }

    val isUsernameValid: Boolean by lazy {
        username.isNotBlank()
    }
    val isPasswordConfirmationValid: Boolean by lazy {
        passwordConfirmation == password
    }

    val isFullNameValid: Boolean by lazy {
        fullName.isNotBlank()
    }

    val isValid by lazy {
        isEmailValid && isPasswordConfirmationValid && isUsernameValid && isPasswordValid && isFullNameValid
    }
}

enum class Error {
    NETWORK,
    WRONG_CREDENTIALS,
    ALREADY_REGISTERED,
    OTHER,
    FIELD;
}