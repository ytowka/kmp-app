package com.example.feature.auth.ui

sealed interface AuthIntent {
    data class OnUsernameChange(val username: String) : AuthIntent
    data class OnPasswordChange(val password: String) : AuthIntent

    // Login
    data object OnLogin : AuthIntent
    data object OnCreateAccount : AuthIntent

    // Register
    data object OnRegister : AuthIntent
    data object ReturnToSignIn : AuthIntent
    data class OnFullNameChange(val fullName: String) : AuthIntent
    data class OnEmailChange(val email: String) : AuthIntent
    data class OnPhoneChange(val phone: String) : AuthIntent
    data class OnImagePicked(val uri: String?) : AuthIntent
    data class OnPasswordConfirmationChange(val passwordConfirmation: String) : AuthIntent

    data class ShowError(val error: Error?) : AuthIntent
}

sealed interface AuthSideEffect {
    data object Authenticated : AuthSideEffect
}