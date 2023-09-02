package com.indev.suntuk.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.indev.suntuk.service.UserService
import com.indev.suntuk.service.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModel(
    private val userService: UserService
): ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        object Idle : UiState
        data class LoggedIn(val user: User) : UiState
    }

    sealed interface UiEvent {
        object NavigateToMain : UiEvent
        data class ShowError(val message: String) : UiEvent
        data class RequestLaunchGoogleSignIn(val intent: Intent) : UiEvent
    }

    sealed interface UiAction {
        object LoginGoogle : UiAction
        data class LoginGoogleResult(val uid: String, val email: String) : UiAction
    }

    private val _userState = userService.liveUser()
    private val _isLoading = MutableStateFlow(false)

    private val _uiFlow = MutableSharedFlow<UiEvent?>(0 )
    val uiFlow = _uiFlow.shareIn(viewModelScope, SharingStarted.Lazily, 0)

    val uiState = combine(_isLoading, _userState) { isLoading, userState ->
        if (isLoading) {
            UiState.Loading
        } else if (userState != null) {
            UiState.LoggedIn(userState)
        } else {
            UiState.Idle
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Idle)
    private fun loginGoogle() {
        viewModelScope.launch(Dispatchers.IO) {
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                .build()
            _isLoading.update { true }
            _uiFlow.emit(UiEvent.RequestLaunchGoogleSignIn(signInIntent))
        }
    }

    private fun loginGoogleResult(uid: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userService.registerLogin()
                .onStart { _isLoading.update { true } }
                .catch { _uiFlow.emit(UiEvent.ShowError(it.message.orEmpty())) }
                .onCompletion { _isLoading.update { false } }
                .collect()
        }
    }

    private fun onCancelLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { false }
        }
    }

}