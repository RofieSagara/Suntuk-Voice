package com.indev.suntuk.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.indev.suntuk.service.StukService
import com.indev.suntuk.service.model.CommentUI
import com.indev.suntuk.service.model.StukUI
import com.indev.suntuk.ui.create.CreateViewModel
import com.indev.suntuk.utils.work.WorkerPostComment
import com.indev.suntuk.utils.work.WorkerPostReply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
class DetailsViewModel(
    private val stukService: StukService,
    private val workManager: WorkManager
): ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        data class StukDetail(
            val error: String,
            val stuk: StukUI?,
            val comment: List<CommentUI>,
            val isLoadMoreComment: Boolean,
            val isLoadingComment: Boolean,
            val loadingReply: List<String>, // This list contains commentID if exist mean comment try to load more reply
            val loadMoreReply: List<String>, // This list contains commentID if exist mean reply still have loadMore
            val isLoading: Boolean,
        ) : UiState
        data class StukDetailComment(
            val textContent: String,
            val error: String,
            val stuk: StukUI?,
            val comment: List<CommentUI>,
            val isLoadMoreComment: Boolean,
            val isLoadingComment: Boolean,
            val loadingReply: List<String>, // This list contains commentID if exist mean comment try to load more reply
            val loadMoreReply: List<String>, // This list contains commentID if exist mean reply still have loadMore
            val isLoading: Boolean,
            val isAnonymous: Boolean,
        ) : UiState
        data class StukDetailReply(
            val textContent: String,
            val error: String,
            val stuk: StukUI?,
            val comment: List<CommentUI>,
            val isLoadMoreComment: Boolean,
            val isLoadingComment: Boolean,
            val loadingReply: List<String>, // This list contains commentID if exist mean comment try to load more reply
            val loadMoreReply: List<String>, // This list contains commentID if exist mean reply still have loadMore
            val isLoading: Boolean,
            val isAnonymous: Boolean,
            val selectedComment: CommentUI,
        ) : UiState
    }

    sealed interface UiEvent {
        data class ShowError(val message: String) : UiEvent
        object NavigateBack : UiEvent
    }

    sealed interface UiAction {
        object NavigateBack : UiAction
        object RequestAddComment: UiAction
        data class RequestAddReply(val commentID: String): UiAction
        object CancelAddComment: UiAction
        object CancelAddReply: UiAction
        object LikeStuk: UiAction
        data class LikeComment(val commentID: String): UiAction
        data class LikeReply(val replyID: String): UiAction
        object LoadMoreComment: UiAction
        data class LoadMoreReply(val commentID: String): UiAction
        object PostComment: UiAction
        object PostReply: UiAction
        data class ChangeTextContent(val text: String): UiAction
    }

    internal sealed interface State {
        object Idle: State
        object Comment: State
        data class Reply(val commentID: String): State
    }

    private val _error = MutableSharedFlow<String>().apply { onEmpty { emit("") } }
    private val _isLoading = MutableStateFlow(false)
    private val _stukDetailID = MutableStateFlow("")
    private val _stukDetail: Flow<StukUI?> = _stukDetailID.flatMapLatest {
        if (it.isNotBlank()) {
            stukService.liveStuk(it)
        } else {
            flowOf(null)
        }
    }
    private val _commentList: Flow<List<CommentUI>> = _stukDetailID.flatMapLatest {
        if (it.isNotBlank()) {
            stukService.liveFetchComment(it)
        } else {
            flowOf(emptyList())
        }
    }
    private val _loadingReply = MutableStateFlow<List<String>>(emptyList())
    private val _loadMoreReply = MutableStateFlow<List<String>>(emptyList())
    private val _isLoadingComment = MutableStateFlow(false)
    private val _isLoadMoreComment = MutableStateFlow(false)
    private val _isAnonymous = MutableStateFlow(false)
    private val _selectedCommentID = MutableStateFlow("")
    private val _selectedComment = _selectedCommentID.flatMapLatest {
        if (it.isNotBlank()) {
            stukService.liveComment(it)
        } else {
            flowOf(null)
        }
    }
    private val _stateCommentReply = MutableStateFlow<State>(State.Idle)
    private val _textContent = MutableStateFlow("")


    @Suppress("UNCHECKED_CAST")
    val uiState = combine(
        _error, _isLoading, _stukDetail, _commentList,
        _loadingReply, _loadMoreReply, _isLoadingComment, _isLoadMoreComment,
        _isAnonymous, _selectedComment, _stateCommentReply, _textContent
    ) { res ->

        val error = res[0] as String
        val isLoading = res[1] as Boolean
        val stukDetails = res[2] as StukUI?
        val comment = res[3] as List<CommentUI>
        val loadingReply = res[4] as List<String>
        val loadMoreReply = res[5] as List<String>
        val isLoadingComment = res[6] as Boolean
        val isLoadMoreComment = res[7] as Boolean
        val isAnonymous = res[8] as Boolean
        val selectedComment = res[9] as CommentUI?
        val state = res[10] as State
        val textContent = res[11] as String

        when (state) {
            is State.Comment -> {
                UiState.StukDetailComment(
                    error = error,
                    stuk = stukDetails,
                    comment = comment,
                    isLoadMoreComment = isLoadMoreComment,
                    isLoadingComment = isLoadingComment,
                    loadingReply = loadingReply,
                    loadMoreReply = loadMoreReply,
                    isLoading = isLoading,
                    isAnonymous = isAnonymous,
                    textContent = textContent
                )
            }
            is State.Reply -> {
                if (selectedComment != null) {
                    UiState.StukDetailReply(
                        error = error,
                        stuk = stukDetails,
                        comment = comment,
                        isLoadMoreComment = isLoadMoreComment,
                        isLoadingComment = isLoadingComment,
                        loadingReply = loadingReply,
                        loadMoreReply = loadMoreReply,
                        isLoading = isLoading,
                        isAnonymous = isAnonymous,
                        selectedComment = selectedComment,
                        textContent = textContent
                    )
                } else {
                    UiState.StukDetail(
                        error = error,
                        stuk = stukDetails,
                        comment = comment,
                        isLoadMoreComment = isLoadMoreComment,
                        isLoadingComment = isLoadingComment,
                        loadingReply = loadingReply,
                        loadMoreReply = loadMoreReply,
                        isLoading = isLoading
                    )
                }
            }
            else -> {
                UiState.StukDetail(
                    error = error,
                    stuk = stukDetails,
                    comment = comment,
                    isLoadMoreComment = isLoadMoreComment,
                    isLoadingComment = isLoadingComment,
                    loadingReply = loadingReply,
                    loadMoreReply = loadMoreReply,
                    isLoading = isLoading
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Loading)

    private val _uiFlow = MutableStateFlow<UiEvent?>(null)
    val uiFlow = _uiFlow.shareIn(viewModelScope, SharingStarted.Lazily, 0)

    private fun likeStuk() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentStukID = _stukDetailID.value
            stukService.likeStuk(currentStukID)
                .catch { _error.emit(it.message ?: "Unknown error") }
                .collect()
        }
    }

    private fun loadMoreComment() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentStukID = _stukDetailID.value
            stukService.loadMoreComment(currentStukID)
                .onEach { _isLoadMoreComment.update { it } }
                .onStart { _isLoadingComment.update { true } }
                .catch { _error.emit(it.message ?: "Unknown error") }
                .onCompletion { _isLoadingComment.update { false } }
                .collect()
        }
    }

    private fun loadReply(commentID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stukService.loadReply(commentID)
                .onEach { isLoadMore ->
                    _loadMoreReply.update {
                        if (isLoadMore) it.toMutableSet().apply { add(commentID) }.toList() else
                            it.toMutableSet().apply { remove(commentID) }.toList()
                    }
                }
                .onStart {
                    _loadingReply.update {
                        it.toMutableSet().apply { add(commentID) }.toList()
                    }
                }
                .catch { _error.emit(it.message ?: "Unknown error") }
                .onCompletion {
                    _loadingReply.update {
                        it.toMutableSet().apply { remove(commentID) }.toList()
                    }
                }
                .collect()
        }
    }

    private fun loadMoreReply(commentID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stukService.loadMoreReply(commentID)
                .onEach { isLoadMore ->
                    _loadMoreReply.update {
                        if (isLoadMore) it.toMutableSet().apply { add(commentID) }.toList() else
                            it.toMutableSet().apply { remove(commentID) }.toList()
                    }
                }
                .onStart {
                    _loadingReply.update {
                        it.toMutableSet().apply { add(commentID) }.toList()
                    }
                }
                .catch { _error.emit(it.message ?: "Unknown error") }
                .onCompletion {
                    _loadingReply.update {
                        it.toMutableSet().apply { remove(commentID) }.toList()
                    }
                }
                .collect()
        }
    }

    private fun likeComment(commentID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stukService.likeComment(commentID)
                .catch { _error.emit(it.message ?: "Unknown error") }
                .collect()
        }
    }

    private fun likeReply(replyID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stukService.likeReply(replyID)
                .catch { _error.emit(it.message ?: "Unknown error") }
                .collect()
        }
    }

    private fun cancelCommentReply() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCommentID.update { "" }
            _stateCommentReply.update { State.Idle }
        }
    }

    private fun requestAddComment() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCommentID.update { "" }
            _stateCommentReply.update { State.Comment }
        }
    }

    private fun requestAddReply(commentID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCommentID.update { commentID }
            _stateCommentReply.update { State.Reply(commentID) }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiFlow.update { UiEvent.NavigateBack }
        }
    }

    private fun resetState() {
        _isLoading.update { false }
        _stateCommentReply.update { State.Idle }
        _selectedCommentID.update { "" }
    }

    private fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            val stukID = _stukDetailID.value.ifBlank { null } ?: return@launch
            val text = _textContent.value.ifBlank { null } ?: return@launch
            val isAnonymous = _isAnonymous.value

            if (_stateCommentReply.value is State.Comment) {
                val params = Data.Builder().apply {
                    putString("stukID", stukID)
                    putString("text", text)
                    putBoolean("isAnonymous", isAnonymous)
                }.build()
                val postCommentWorker = OneTimeWorkRequestBuilder<WorkerPostComment>()
                    .setInputData(params)
                    .build()
                workManager.enqueue(postCommentWorker)
                resetState()
            } else if (_stateCommentReply.value is State.Reply && _selectedCommentID.value.isNotBlank()){
                val commentID = _selectedCommentID.value.ifBlank { null } ?: return@launch
                val params = Data.Builder().apply {
                    putString("commentID", commentID)
                    putString("text", text)
                    putBoolean("isAnonymous", isAnonymous)
                }.build()
                val postReplyWorker = OneTimeWorkRequestBuilder<WorkerPostReply>()
                    .setInputData(params)
                    .build()
                workManager.enqueue(postReplyWorker)
                resetState()
            }
        }
    }

    private fun changeTextContent(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _textContent.update { text }
        }
    }

    fun onAction(action: UiAction) {
        when (action) {
            UiAction.NavigateBack -> navigateBack()
            UiAction.RequestAddComment -> requestAddComment()
            is UiAction.RequestAddReply -> requestAddReply(action.commentID)
            UiAction.CancelAddComment -> cancelCommentReply()
            UiAction.CancelAddReply -> cancelCommentReply()
            is UiAction.LikeComment -> likeComment(action.commentID)
            is UiAction.LikeReply -> likeReply(action.replyID)
            is UiAction.LikeStuk -> likeStuk()
            UiAction.LoadMoreComment -> loadMoreComment()
            is UiAction.LoadMoreReply -> loadMoreReply(action.commentID)
            UiAction.PostComment -> post()
            UiAction.PostReply -> post()
            is UiAction.ChangeTextContent -> changeTextContent(action.text)
        }
    }
}