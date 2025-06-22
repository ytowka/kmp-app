package com.example.kmpapp.android.feature.auth.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature.auth.ui.AuthIntent
import com.example.feature.auth.ui.AuthSideEffect
import com.example.feature.auth.ui.AuthViewModel
import com.example.feature.auth.ui.Form
import com.example.kmpapp.android.coreui.injectViewModel
import com.example.kmpapp.android.coreui.onSideEffect

@Composable
fun AuthScreen(
    viewModel: AuthViewModel= injectViewModel(),
    onLoginSuccess: () -> Unit,
){
    val state by viewModel.state.collectAsState()

    viewModel.onSideEffect {
        when(it) {
            AuthSideEffect.Authenticated -> onLoginSuccess()
        }
    }

    val loginCallback = remember {
        object : LoginFormCallback {
            override fun onUsernameChange(username: String) {
                viewModel.accept(AuthIntent.OnUsernameChange(username))
            }

            override fun onPasswordChange(password: String) {
                viewModel.accept(AuthIntent.OnPasswordChange(password))
            }

            override fun onLogin() {
                viewModel.accept(AuthIntent.OnLogin)
            }

            override fun onCreateAccount() {
                viewModel.accept(AuthIntent.OnCreateAccount)
            }
        }
    }

    val registerCallback = remember {
        object : RegisterFormCallback {
            override fun onRegister() {
                viewModel.accept(AuthIntent.OnRegister)
            }

            override fun returnToSignIn() {
                viewModel.accept(AuthIntent.ReturnToSignIn)
            }

            override fun onUsernameChange(username: String) {
                viewModel.accept(AuthIntent.OnUsernameChange(username))
            }

            override fun onPasswordChange(password: String) {
                viewModel.accept(AuthIntent.OnPasswordChange(password))
            }

            override fun onFullNameChange(fullName: String) {
                viewModel.accept(AuthIntent.OnFullNameChange(fullName))
            }

            override fun onEmailChange(email: String) {
                viewModel.accept(AuthIntent.OnEmailChange(email))
            }

            override fun onPhoneChange(phone: String) {
                viewModel.accept(AuthIntent.OnPhoneChange(phone))
            }

            override fun onImagePicked(uri: String?) {
                viewModel.accept(AuthIntent.OnImagePicked(uri))
            }

            override fun onPasswordConfirmationChange(passwordConfirmation: String) {
                viewModel.accept(AuthIntent.OnPasswordConfirmationChange(passwordConfirmation))
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(state.currentForm){ targetState: Form ->
            when(targetState){
                Form.LOGIN -> LoginForm(
                    loginState = state.loginState,
                    callback = loginCallback
                )
                Form.REGISTRATION -> RegisterForm(
                    registerState = state.registerState,
                    callback = registerCallback
                )
            }
        }
    }
}
