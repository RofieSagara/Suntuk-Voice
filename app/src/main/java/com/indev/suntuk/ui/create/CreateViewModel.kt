package com.indev.suntuk.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.indev.suntuk.service.StukService
import com.indev.suntuk.utils.work.WorkerPostStuk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateViewModel(
    private val stukService: StukService,
    private val workManager: WorkManager,
): ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        data class StateText(
            val text: String,
            val isLoading: Boolean,
            val isAnonymous: Boolean,
            val error: String,
        )
        data class StateImage(
            val text: String,
            val imagePath: List<String>,
            val isLoading: Boolean,
            val isAnonymous: Boolean,
            val error: String,
        )
        data class StateVoice(
            val text: String,
            val voicePath: String,
            val isLoading: Boolean,
            val isAnonymous: Boolean,
            val error: String,
        )
    }

    sealed interface UiEvent {
        data class ShowError(val message: String) : UiEvent
        object NavigateBack : UiEvent
    }

    sealed interface UiAction {
        object CreateStuk : UiAction
        data class ChangeText(val text: String) : UiAction
        data class AddImage(val imagePath: String) : UiAction
        data class RemoveImage(val imagePath: String) : UiAction
        data class SetVoice(val voicePath: String) : UiAction
        object RemoveVoice : UiAction
        data class SetAnonymous(val isAnonymous: Boolean) : UiAction
        object Post : UiAction
    }

    private val _isAnonymous = MutableStateFlow(false)
    private val _text = MutableStateFlow("")
    private val _imagePath = MutableStateFlow<List<String>>(emptyList())
    private val _voicePath = MutableStateFlow("")
    private val _error = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)

    @Suppress("UNCHECKED_CAST")
    val uiState = combine(_isAnonymous, _text, _imagePath, _voicePath, _isLoading) { params ->
        val isAnonymous = params[0] as Boolean
        val text = params[1] as String
        val imagePath = params[2] as List<String>
        val voicePath = params[3] as String
        val isLoading = params[4] as Boolean

        if (text.isBlank() && imagePath.isEmpty() && voicePath.isBlank()) {
            UiState.Loading
        } else if (imagePath.isEmpty() && voicePath.isBlank()) {
            UiState.StateText(
                text = text,
                isLoading = isLoading,
                isAnonymous = isAnonymous,
                error = ""
            )
        } else if (imagePath.isNotEmpty() && voicePath.isBlank()) {
            UiState.StateImage(
                text = text,
                imagePath = imagePath,
                isLoading = isLoading,
                isAnonymous = isAnonymous,
                error = ""
            )
        } else if (imagePath.isEmpty() && voicePath.isNotBlank()) {
            UiState.StateVoice(
                text = text,
                voicePath = voicePath,
                isLoading = isLoading,
                isAnonymous = isAnonymous,
                error = ""
            )
        } else {
            UiState.Loading
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Loading)

    private val _uiFlow = MutableStateFlow<UiEvent?>(null)
    val uiFlow = _uiFlow.shareIn(viewModelScope, SharingStarted.Lazily, 0)

    private fun changeText(text: String) {
        viewModelScope.launch {
            _text.update { text }
        }
    }

    private fun addImage(imagePath: String) {
        viewModelScope.launch {
            _imagePath.update { it.plus(imagePath) }
        }
    }

    private fun removeImage(imagePath: String) {
        viewModelScope.launch {
            _imagePath.update { it.minusElement(imagePath) }
        }
    }

    private fun setVoice(voicePath: String) {
        viewModelScope.launch {
            _voicePath.update { voicePath }
        }
    }

    private fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            val text = _text.value
            val imagePath = _imagePath.value
            val voicePath = _voicePath.value
            val isAnonymous = _isAnonymous.value

            if (text.isBlank()) {
                _uiFlow.update { UiEvent.ShowError("Text is empty") }
                return@launch
            }

            if (text.isBlank() && imagePath.isEmpty() && voicePath.isBlank()) {
                _uiFlow.update { UiEvent.ShowError("There is no content") }
            } else {
                val params = Data.Builder().apply {
                    putString("text", text)
                    putStringArray("imagePath", imagePath.toTypedArray())
                    putString("voicePath", voicePath)
                    putBoolean("isAnonymous", isAnonymous)
                }.build()
                val postStukWorker = OneTimeWorkRequestBuilder<WorkerPostStuk>()
                    .setInputData(params)
                    .build()
                workManager.enqueue(postStukWorker)
                _uiFlow.update { UiEvent.NavigateBack }
            }
        }
    }

    private fun setAnonymous(isAnonymous: Boolean) {
        viewModelScope.launch {
            _isAnonymous.update { isAnonymous }
        }
    }

    fun onAction(action: UiAction) {
        when (action) {
            is UiAction.CreateStuk -> post()
            is UiAction.ChangeText -> changeText(action.text)
            is UiAction.AddImage -> addImage(action.imagePath)
            is UiAction.RemoveImage -> removeImage(action.imagePath)
            is UiAction.SetVoice -> setVoice(action.voicePath)
            is UiAction.RemoveVoice -> setVoice("")
            is UiAction.SetAnonymous -> setAnonymous(action.isAnonymous)
            is UiAction.Post -> post()
        }
    }

}