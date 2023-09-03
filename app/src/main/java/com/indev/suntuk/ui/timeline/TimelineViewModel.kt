package com.indev.suntuk.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indev.suntuk.service.StukService
import com.indev.suntuk.service.model.CommentUI
import com.indev.suntuk.service.model.StukUI
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
class TimelineViewModel(
    private val stukService: StukService
) : ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        data class TimelineList(val timeline: List<StukUI>, val isLoading: Boolean) : UiState
        data class OpenStukDetail(
            val stuk: StukUI,
            val timeline: List<StukUI>,
            val comment: List<CommentUI>,
            val isLoadMoreComment: Boolean,
            val isLoadingComment: Boolean,
            val loadingReply: List<String>, // This list contains commentID if exist mean comment try to load more reply
            val loadMoreReply: List<String>, // This list contains commentID if exist mean reply still have loadMore
            val isLoading: Boolean,
        ) : UiState
        data class Error(val message: String) : UiState
    }

    sealed interface UiEffect {
        data class OpenStukDetail(val stukID: String) : UiEffect
        object NavigateAddStuk : UiEffect
        data class NavigateComment(val stukID: String): UiEffect
    }

    sealed interface UiEvent {
        object LoadTimeline : UiEvent
        object LoadMoreTimeline : UiEvent
        data class LikeStuk(val stukID: String) : UiEvent
        data class OpenStukDetail(val stukID: String) : UiEvent
        object CloseStukDetail : UiEvent
        object LoadMoreComment : UiEvent
        data class LikeComment(val commentID: String) : UiEvent
        data class LoadReply(val commentID: String) : UiEvent
        data class LoadMoreReply(val commentID: String) : UiEvent
        data class LikeReply(val replyID: String) : UiEvent
        object NavigationAddStuk : UiEvent
        data class NavigationComment(val stukID: String): UiEvent
    }

    private val _error = MutableSharedFlow<String>().apply { onEmpty { emit("") } }
    private val _isLoading = MutableStateFlow(false)
    private val _timeline = stukService.liveFetchTimeline()
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

    @Suppress("UNCHECKED_CAST")
    val uiState = combine(
        _error, _isLoading, _timeline, _stukDetail, _commentList,
        _loadingReply, _loadMoreReply, _isLoadingComment, _isLoadMoreComment
    ) { res ->

        val err = res[0] as String
        val isLoading = res[1] as Boolean
        val timeline = res[2] as List<StukUI>
        val stukDetails = res[3] as StukUI?
        val comment = res[4] as List<CommentUI>
        val loadingReply = res[5] as List<String>
        val loadMoreReply = res[6] as List<String>
        val isLoadingComment = res[7] as Boolean
        val isLoadMoreComment = res[8] as Boolean

        if (err.isNotEmpty()) {
            UiState.Error(err)
        } else if (stukDetails != null) {
            UiState.OpenStukDetail(
                stuk = stukDetails,
                timeline = timeline,
                comment = comment,
                isLoadMoreComment = isLoadMoreComment,
                isLoadingComment = isLoadingComment,
                loadingReply = loadingReply,
                loadMoreReply = loadMoreReply,
                isLoading = isLoading
            )
        } else {
            UiState.TimelineList(timeline, isLoading)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Loading)

    private val _uiFlow = MutableStateFlow<UiEffect?>(null)
    val uiFlow = _uiFlow.shareIn(viewModelScope, SharingStarted.Lazily, 0)

    private fun loadTimeline() {
        viewModelScope.launch(Dispatchers.IO) {
            stukService.loadTimeline()
                .onStart { _isLoading.emit(true) }
                .catch { _error.emit(it.message ?: "Unknown error") }
                .onCompletion { _isLoading.emit(false) }
                .collect()
        }
    }

    private fun loadMoreTimeline() {
        viewModelScope.launch(Dispatchers.IO) {
            stukService.loadMoreTimeline()
                .onStart { _isLoading.emit(true) }
                .catch { _error.emit(it.message ?: "Unknown error") }
                .onCompletion { _isLoading.emit(false) }
                .collect()
        }
    }

    private fun likeStuk(stukID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stukService.likeStuk(stukID)
                .catch { _error.emit(it.message ?: "Unknown error") }
                .collect()
        }
    }

    private fun openStukDetail(stukID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _stukDetailID.update { stukID }
            stukService.loadComment(stukID)
                .onEach { _isLoadMoreComment.update { it } }
                .onStart { _isLoadingComment.update { true } }
                .catch { _error.emit(it.message ?: "Unknown error") }
                .onCompletion { _isLoadingComment.update { false } }
                .collect()
        }
    }

    private fun closeStukDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            _stukDetailID.update { "" }
        }
    }

    private fun loadMoreComment() {
        viewModelScope.launch(Dispatchers.IO) {
            _stukDetailID.value.run {
                stukService.loadMoreComment(this)
                    .onEach { _isLoadMoreComment.update { it } }
                    .onStart { _isLoadingComment.update { true } }
                    .catch { _error.emit(it.message ?: "Unknown error") }
                    .onCompletion { _isLoadingComment.update { false } }
                    .collect()
            }
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

    private fun navigateComment(stukID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiFlow.emit(UiEffect.NavigateComment(stukID))
        }
    }

    private fun navigateAddStuk() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiFlow.emit(UiEffect.NavigateAddStuk)
        }
    }

    fun onAction(action: UiEvent) {
        when (action) {
            UiEvent.LoadTimeline -> loadTimeline()
            UiEvent.LoadMoreTimeline -> loadMoreTimeline()
            is UiEvent.LikeStuk -> likeStuk(action.stukID)
            is UiEvent.OpenStukDetail -> openStukDetail(action.stukID)
            is UiEvent.CloseStukDetail -> closeStukDetail()
            UiEvent.LoadMoreComment -> loadMoreComment()
            is UiEvent.LikeComment -> likeComment(action.commentID)
            is UiEvent.LikeReply -> likeReply(action.replyID)
            is UiEvent.LoadMoreReply -> loadMoreReply(action.commentID)
            is UiEvent.LoadReply -> loadReply(action.commentID)
            is UiEvent.NavigationComment -> navigateComment(action.stukID)
            UiEvent.NavigationAddStuk -> navigateAddStuk()
        }
    }
}