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
import com.example.feature.auth.ui.AuthViewModel
import com.example.feature.auth.ui.Form
import kotlinx.coroutines.flow.first
import org.koin.androidx.compose.koinViewModel
import com.example.kmpapp.android.coreui.injectViewModel


@Composable
fun AuthScreen(
    viewModel: AuthViewModel= injectViewModel(),
    onLoginSuccess: () -> Unit,
){
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.successLogin.first { it }
        onLoginSuccess()
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
                    callback = viewModel
                )
                Form.REGISTER -> RegisterForm(
                    registerState = state.registerState,
                    callback = viewModel
                )
            }
        }
    }
}
