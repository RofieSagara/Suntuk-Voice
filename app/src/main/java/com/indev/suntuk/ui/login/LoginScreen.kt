package com.indev.suntuk.ui.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.indev.suntuk.ui.login.component.LoginBody
import com.indev.suntuk.ui.login.component.LoginHeader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavTimeline: ()->Unit
) {
    val state by viewModel.uiState.collectAsState()

    LoginScreenContent(
        state = state,
        effect = viewModel.uiEffect,
        onEvent = viewModel::onEvent,
        onNavTimeline = onNavTimeline
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenContent(
    state: LoginViewModel.UiState,
    effect: Flow<LoginViewModel.UiEffect>,
    onEvent: (LoginViewModel.UiEvent) -> Unit,
    onNavTimeline: () -> Unit
) {

    val event by rememberUpdatedState(onEvent)

    val googleSignIn = rememberLauncherForActivityResult(FirebaseAuthUIActivityResultContract()) {
        event.invoke(LoginViewModel.UiEvent.LoginWithGoogleSuccess)
    }
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when(effect) {
                LoginViewModel.UiEffect.CancelLogin -> {
                    snackbarHostState.showSnackbar("Login cancelled")
                }
                is LoginViewModel.UiEffect.Error -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is LoginViewModel.UiEffect.LaunchFirebaseUI -> {
                    googleSignIn.launch(effect.intent)
                }
                is LoginViewModel.UiEffect.NavigateToTimeline -> {
                    // This should be navigate to timeline too
                    onNavTimeline()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LoginHeader(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f, true))
            LoginBody(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { event.invoke(LoginViewModel.UiEvent.LoginWithGoogle) },
                enabled = state !is LoginViewModel.UiState.Loading
            ) {
                Text(text = "Login with Google")
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
private fun LoginScreenPreview() {
    LoginScreenContent(
        state = LoginViewModel.UiState.Idle,
        effect = emptyFlow(),
        onEvent = {},
        onNavTimeline = {}
    )
}