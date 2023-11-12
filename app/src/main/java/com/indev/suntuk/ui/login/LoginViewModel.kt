package com.indev.suntuk.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.indev.suntuk.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userService: UserService
): ViewModel() {

    sealed interface UiState {
        object Idle: UiState
        object Loading: UiState
        object Success: UiState
        data class Error(val message: String): UiState
    }

    sealed interface UiEvent {
        object LoginWithGoogle: UiEvent
        object CancelLogin: UiEvent
        object LoginWithGoogleSuccess: UiEvent
    }

    sealed interface UiEffect {
        data class LaunchFirebaseUI(val intent: Intent): UiEffect
        object NavigateToTimeline: UiEffect
        object CancelLogin: UiEffect
        data class Error(val message: String): UiEffect
        object Nothing: UiEffect
    }

    private val _error = MutableSharedFlow<String>().apply { onEmpty { emit("") } }
    private val _isLoading = MutableStateFlow(false)
    private val _currentUser = userService.liveUser()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val uiState = combine(_error, _isLoading, _currentUser) { error, isLoading, currentUser ->
        when {
            isLoading -> UiState.Loading
            error.isNotBlank() -> UiState.Error(error)
            currentUser != null -> UiState.Success
            else -> UiState.Idle
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Idle)

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.shareIn(viewModelScope, SharingStarted.Lazily, 0)

    private fun requestLoginWithGoogle() {
        viewModelScope.launch(Dispatchers.IO) {
            AuthUI.getInstance().apply {
                // Put auth to firebase local if this in debug mode
                // authUI.useEmulator("10.0.2.2", 9099)
            }.createSignInIntentBuilder()
                .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                .build().run {
                    _uiEffect.emit(UiEffect.LaunchFirebaseUI(this))
                }
            _isLoading.update { true }
        }
    }

    private fun cancelLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEffect.emit(UiEffect.CancelLogin)
            _isLoading.update { false }
        }
    }

    private fun successLogin() {
        // Register or login the user
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                userService.registerLogin()
                    .onStart { _isLoading.update { false } }
                    .onEach { _uiEffect.emit(UiEffect.NavigateToTimeline) }
                    .catch {
                        _uiEffect.emit(UiEffect.Error(it.message ?: "Failed to register with Google"))
                    }.onCompletion {
                        _isLoading.update { false }
                    }.collect()
            } else {
                _isLoading.update { false }
                _uiEffect.emit(UiEffect.Error("Failed to login with Google"))
            }
        }
    }

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.LoginWithGoogle -> requestLoginWithGoogle()
            is UiEvent.CancelLogin -> cancelLogin()
            is UiEvent.LoginWithGoogleSuccess -> successLogin()
        }
    }
}