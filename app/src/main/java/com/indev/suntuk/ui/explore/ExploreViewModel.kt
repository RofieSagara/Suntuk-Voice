package com.indev.suntuk.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indev.suntuk.service.StukService
import com.indev.suntuk.service.model.CommentUI
import com.indev.suntuk.service.model.ReplyUI
import com.indev.suntuk.service.model.StukUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ExploreViewModel(
    private val stukService: StukService
): ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        data class Search(
            val dataStuk: List<StukUI>,
            val dataComment: List<CommentUI>,
            val dataReply: List<ReplyUI>,
            val isLoading: Boolean,
            val error: String
        ) : UiState

        data class SearchStuk(
            val dataStuk: List<StukUI>,
            val isLoadMore: Boolean,
        )

        data class SearchComment(
            val dataComment: List<CommentUI>,
            val isLoadMore: Boolean,
        )

        data class SearchReply(
            val dataReply: List<ReplyUI>,
            val isLoadMore: Boolean,
        )

        object NoData : UiState
    }

    sealed interface UiEffect {
        data class NavigateToDetail(val stukID: String) : UiEffect
    }

    sealed interface UiEvent {
        data class Search(val keyword: String) : UiEvent
        data class SearchStuk(val keyword: String) : UiEvent
        data class SearchComment(val keyword: String) : UiEvent
        data class SearchReply(val keyword: String) : UiEvent
        object LoadMore: UiEvent
        data class NavigateToDetail(val stukID: String) : UiEvent
    }

    private val _keyword = MutableStateFlow("")
    private val _type = MutableStateFlow("ALL")
    private val _loadingStuk = MutableStateFlow(false)
    private val _loadingComment = MutableStateFlow(false)
    private val _loadingReply = MutableStateFlow(false)
    private val _isLoading = combine(
        _loadingStuk,
        _loadingComment,
        _loadingReply
    ) { loadingStuk, loadingComment, loadingReply ->
        loadingStuk || loadingComment || loadingReply
    }
    private val _error = MutableSharedFlow<String>().apply { onEmpty { emit("") } }
    private val _stuk = _keyword.flatMapLatest {
        if (it.isNotBlank() && (_type.value == "ALL" || _type.value == "STUK")) {
            stukService.searchStuk(it)
        } else {
            flowOf(listOf())
        }
    }.onStart { _loadingStuk.update { true } }
        .catch { }
        .onCompletion { _loadingStuk.update { false } }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    private val _comment = _keyword.flatMapLatest {
        if (it.isNotBlank() && (_type.value == "ALL" || _type.value == "COMMENT")) {
            stukService.searchComment(it)
        } else {
            flowOf(listOf())
        }
    }.onStart { _loadingComment.update { true } }
        .catch { }
        .onCompletion { _loadingComment.update { false } }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    private val _reply = _keyword.flatMapLatest {
        if (it.isNotBlank() && (_type.value == "ALL" || _type.value == "REPLY")) {
            stukService.searchReply(it)
        } else {
            flowOf(listOf())
        }
    }.onStart { _loadingReply.update { true } }
        .catch { }
        .onCompletion { _loadingReply.update { false } }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    @Suppress("UNCHECKED_CAST")
    val uiState = combine(
        _stuk,
        _comment,
        _reply,
        _isLoading,
        _error,
        _type
    ) { values ->

        val stuk = values[0] as List<StukUI>
        val comment = values[1] as List<CommentUI>
        val reply = values[2] as List<ReplyUI>
        val isLoading = values[3] as Boolean
        val error = values[4] as String

        when (values[5] as String) {
            "ALL" -> {
                UiState.Search(
                    dataStuk = stuk,
                    dataComment = comment,
                    dataReply = reply,
                    isLoading = isLoading,
                    error = error
                )
            }
            "STUK" -> {
                UiState.SearchStuk(
                    dataStuk = stuk,
                    isLoadMore = isLoading
                )
            }
            "COMMENT" -> {
                UiState.SearchComment(
                    dataComment = comment,
                    isLoadMore = isLoading
                )
            }
            "REPLY" -> {
                UiState.SearchReply(
                    dataReply = reply,
                    isLoadMore = isLoading
                )
            }
            else -> {
                UiState.NoData
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Loading)

    private val _uiFlow = MutableStateFlow<UiEffect?>(null)
    val uiFlow = _uiFlow.shareIn(viewModelScope, SharingStarted.Lazily, 0)

    private fun search(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _keyword.update { keyword }
            _type.update { "ALL" }
        }
    }

    private fun searchStuk(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _keyword.update { keyword }
            _type.update { "STUK" }
        }
    }

    private fun searchComment(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _keyword.update { keyword }
            _type.update { "COMMENT" }
        }
    }

    private fun searchReply(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _keyword.update { keyword }
            _type.update { "REPLY" }
        }
    }

    private fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentKeyword = _keyword.value
            when (_type.value) {
                "STUK" -> {
                    flowOf(
                        requireNotNull(_stuk.value.lastOrNull()?.stuk?.id)
                    ).flatMapLatest {
                        stukService.searchStuk(currentKeyword, it)
                    }.onStart { _loadingStuk.update { true } }
                        .catch { }
                        .onCompletion { _loadingStuk.update { false } }
                        .collect()
                }

                "COMMENT" -> {
                    flowOf(requireNotNull(_comment.value.lastOrNull()?.comment?.id)).flatMapLatest {
                        stukService.searchComment(currentKeyword, it)
                    }.onStart { _loadingComment.update { true } }
                        .catch { }
                        .onCompletion { _loadingComment.update { false } }
                        .collect()
                }

                "REPLY" -> {
                    flowOf(requireNotNull(_reply.value.lastOrNull()?.reply?.id)).flatMapLatest {
                        stukService.searchReply(currentKeyword, it)
                    }.onStart { _loadingReply.update { true } }
                        .catch { }
                        .onCompletion { _loadingReply.update { false } }
                        .collect()
                }
            }
        }
    }

    private fun navigateToDetail(stukID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiFlow.update { UiEffect.NavigateToDetail(stukID) }
        }
    }

    fun onAction(action: UiEvent) {
        when (action) {
            is UiEvent.Search -> {
                search(action.keyword)
            }
            is UiEvent.SearchStuk -> {
                searchStuk(action.keyword)
            }
            is UiEvent.SearchComment -> {
                searchComment(action.keyword)
            }
            is UiEvent.SearchReply -> {
                searchReply(action.keyword)
            }
            UiEvent.LoadMore -> {
                loadMore()
            }
            is UiEvent.NavigateToDetail -> {
                navigateToDetail(action.stukID)
            }
        }
    }
}