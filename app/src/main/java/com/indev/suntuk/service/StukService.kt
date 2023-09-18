package com.indev.suntuk.service

import com.indev.suntuk.service.api.CommentAPI
import com.indev.suntuk.service.api.ReplyAPI
import com.indev.suntuk.service.api.StukAPI
import com.indev.suntuk.service.local.CommentLikeRepository
import com.indev.suntuk.service.local.CommentRepository
import com.indev.suntuk.service.local.ReplyLikeRepository
import com.indev.suntuk.service.local.ReplyRepository
import com.indev.suntuk.service.local.StukDeletedRepository
import com.indev.suntuk.service.local.StukLikeRepository
import com.indev.suntuk.service.local.StukRepository
import com.indev.suntuk.service.local.entity.stuk.StukEntity
import com.indev.suntuk.service.local.entity.stuk.StukLikeEntity
import com.indev.suntuk.service.local.entity.stuk.fromResponse
import com.indev.suntuk.service.model.Comment
import com.indev.suntuk.service.model.CommentLike
import com.indev.suntuk.service.model.CommentUI
import com.indev.suntuk.service.model.ReplyUI
import com.indev.suntuk.service.model.Stuk
import com.indev.suntuk.service.model.StukLike
import com.indev.suntuk.service.model.StukUI
import com.indev.suntuk.service.model.fromEntity
import com.indev.suntuk.utils.asResultFlowThrow
import com.indev.suntuk.utils.liveStukConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.io.File

interface StukService {
    fun liveFetchTimeline(): Flow<List<StukUI>>
    fun liveFetchOwner(): Flow<List<StukUI>>
    fun liveFetchLike(): Flow<List<StukUI>>
    fun liveStuk(stukID: String): Flow<StukUI?>

    fun loadStukLikeNewer(): Flow<Boolean>
    fun loadStukLike(): Flow<Boolean>
    fun loadMoreStukLike(): Flow<Boolean>
    fun likeStuk(stukID: String): Flow<Unit>
    fun loadTimelineNewer(): Flow<Boolean>
    fun loadTimeline(): Flow<Boolean>
    fun loadMoreTimeline(): Flow<Boolean>

    // searchStuk its not save to local so we just request to backend and show it
    // that why we need manual pagination with lastID: String
    fun searchStuk(query: String, lastID: String): Flow<List<StukUI>>
    fun searchStuk(query: String): Flow<List<StukUI>>

    fun postStukText(text: String, isAnonymous: Boolean): Flow<Unit>
    fun postStukImage(text: String, image: List<String>, isAnonymous: Boolean): Flow<Unit>
    fun postStukVoice(text: String, voice: String, isAnonymous: Boolean): Flow<Unit>
    fun deleteStuk(stukID: String): Flow<Unit>

    fun postStukThread(stukID: String, text: String): Flow<Unit>
    fun postStukThreadImage(stukID: String, text: String, image: List<String>): Flow<Unit>
    fun postStukThreadVoice(stukID: String, text: String, voice: String): Flow<Unit>
    fun deleteStukThread(stukID: String, threadID: String): Flow<Unit>

    fun quoteStuk(stukID: String, text: String, isAnonymous: Boolean): Flow<Unit>

    // Comment
    fun liveFetchComment(stukID: String): Flow<List<CommentUI>>
    fun liveComment(commentID: String): Flow<CommentUI>

    fun loadComment(stukID: String): Flow<Boolean>
    fun loadMoreComment(stukID: String): Flow<Boolean>

    fun likeComment(commentID: String): Flow<Unit>

    // searchStuk its not save to local so we just request to backend and show it
    // that why we need manual pagination with lastID: String
    fun searchComment(query: String, lastID: String): Flow<List<CommentUI>>
    fun searchComment(query: String): Flow<List<CommentUI>>

    fun postCommentText(stukID: String, text: String, isAnonymous: Boolean): Flow<Unit>
    fun deleteComment(commentID: String): Flow<Unit>

    //Reply
    fun loadReply(commentID: String): Flow<Boolean>
    fun loadMoreReply(commentID: String): Flow<Boolean>

    fun likeReply(replyID: String): Flow<Unit>

    fun searchReply(query: String, lastID: String): Flow<List<ReplyUI>>
    fun searchReply(query: String): Flow<List<ReplyUI>>

    fun postReplyText(commentID: String, text: String, isAnonymous: Boolean): Flow<Unit>
    fun deleteReply(replyID: String): Flow<Unit>
}

@OptIn(ExperimentalCoroutinesApi::class)
class IStukService(
    private val stukAPI: StukAPI,
    private val commentAPI: CommentAPI,
    private val replyAPI: ReplyAPI,
    private val stukRepository: StukRepository,
    private val stukLikeRepository: StukLikeRepository,
    private val stukDeletedRepository: StukDeletedRepository,
    private val commentRepository: CommentRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val replyRepository: ReplyRepository,
    private val replyLikeRepository: ReplyLikeRepository
): StukService {

    @Suppress("UNCHECKED_CAST")
    private fun liveTimeline(source: Flow<List<StukEntity>>): Flow<List<StukUI>> {
        return source
            .flatMapLatest { stukEntityList ->
                stukLikeRepository.liveFetchUserLikeByStukID(stukEntityList.map { it.id })
                    .map { stukLikeEntityList ->
                        mapOf(
                            "stukEntityList" to stukEntityList.map { Stuk.fromEntity(it) },
                            "stukLikeEntityList" to stukLikeEntityList.map { StukLike.fromEntity(it) }
                        )
                    }
            }.flatMapLatest { stukHolder ->
                val stukList = stukHolder["stukEntityList"] as List<Stuk>
                val stukLikeList = stukHolder["stukLikeEntityList"] as List<StukLike>
                val parentIDList = stukList.filterNot { it.parentId.isNullOrBlank() }
                    .mapNotNull { it.parentId }
                // TODO if not found on the local please request to the backend
                stukRepository.liveFetchByIds(parentIDList)
                    .map { stukParentEntityList ->
                        mapOf(
                            "stukEntityList" to stukList,
                            "stukLikeEntityList" to stukLikeList,
                            "stukParentEntityList" to stukParentEntityList.map { Stuk.fromEntity(it) }
                        )
                    }
            }.flatMapLatest { stukHolder ->
                val stukList = stukHolder["stukEntityList"] as List<Stuk>
                val stukLikeList = stukHolder["stukLikeEntityList"] as List<StukLike>
                val stukParentList = stukHolder["stukParentEntityList"] as List<Stuk>
                stukLikeRepository.liveFetchUserLikeByStukID(stukParentList.map { it.id })
                    .map { stukLikeParentEntityList ->
                        mapOf(
                            "stukEntityList" to stukList,
                            "stukLikeEntityList" to stukLikeList,
                            "stukParentEntityList" to stukParentList,
                            "stukLikeParentEntityList" to stukLikeParentEntityList.map { StukLike.fromEntity(it) }
                        )
                    }
            }.flatMapLatest { stukHolder ->
                val stukList = stukHolder["stukEntityList"] as List<Stuk>
                val stukLikeList = stukHolder["stukLikeEntityList"] as List<StukLike>
                val stukParentList = stukHolder["stukParentEntityList"] as List<Stuk>
                val stukLikeParentList = stukHolder["stukLikeParentEntityList"] as List<StukLike>
                commentRepository.liveLatestCommentByStukIDs(stukList.map { it.id })
                    .map { commentEntityList ->
                        mapOf(
                            "stukEntityList" to stukList,
                            "stukLikeEntityList" to stukLikeList,
                            "stukParentEntityList" to stukParentList,
                            "stukLikeParentEntityList" to stukLikeParentList,
                            "commentEntityList" to commentEntityList.map { Comment.fromEntity(it) }
                        )
                    }
            }.flatMapLatest { stukHolder ->
                val stukList = stukHolder["stukEntityList"] as List<Stuk>
                val stukLikeList = stukHolder["stukLikeEntityList"] as List<StukLike>
                val stukParentList = stukHolder["stukParentEntityList"] as List<Stuk>
                val stukLikeParentList = stukHolder["stukLikeParentEntityList"] as List<StukLike>
                val commentList = stukHolder["commentEntityList"] as List<Comment>
                commentLikeRepository.liveFetchUserLikeByCommentIDs(commentList.map { it.id })
                    .map { commentLikeEntityList ->
                        mapOf(
                            "stukEntityList" to stukList,
                            "stukLikeEntityList" to stukLikeList,
                            "stukParentEntityList" to stukParentList,
                            "stukLikeParentEntityList" to stukLikeParentList,
                            "commentEntityList" to commentList,
                            "commentLikeEntityList" to commentLikeEntityList.map { CommentLike.fromEntity(it) }
                        )
                    }
            }.map { stukHolder ->
                val stukList = stukHolder["stukEntityList"] as List<Stuk>
                val stukLikeList = stukHolder["stukLikeEntityList"] as List<StukLike>
                val stukParentList = stukHolder["stukParentEntityList"] as List<Stuk>
                val stukLikeParentList = stukHolder["stukLikeParentEntityList"] as List<StukLike>
                val commentList = stukHolder["commentEntityList"] as List<Comment>
                val commentLikeList = stukHolder["commentLikeEntityList"] as List<CommentLike>

                stukList.map { stuk ->
                    val parentSelected = stukParentList.firstOrNull { it.id == stuk.parentId }
                    val parentLikeSelected = parentSelected?.let { parent -> stukLikeParentList.firstOrNull { it.stukId == parent.id } }
                    val commentSelected = commentList.firstOrNull { it.stukID == stuk.id }
                    val commentLikeSelected = commentSelected?.let { comment -> commentLikeList.firstOrNull { it.commentID == comment.id } }
                    StukUI(
                        stuk = stuk,
                        stukLike = stukLikeList.firstOrNull { it.stukId == stuk.id },
                        parentStuk = parentSelected,
                        parentStukLike = parentLikeSelected,
                        stukComment = commentSelected,
                        stukCommentLike = commentLikeSelected
                    )
                }
            }
    }

    override fun liveFetchTimeline(): Flow<List<StukUI>> {
        return liveTimeline(stukRepository.liveFetchTimeline())
    }

    override fun liveFetchOwner(): Flow<List<StukUI>> {
        return liveTimeline(stukRepository.liveFetchOwner())
    }

    override fun liveFetchLike(): Flow<List<StukUI>> {
        return liveTimeline(stukRepository.liveFetchFavorite())
    }

    override fun liveStuk(stukID: String): Flow<StukUI?> {
        return stukRepository.liveById(stukID).filterNotNull().flatMapConcat {
            liveTimeline(flowOf(listOf(it))).map { data -> data.firstOrNull() }
        }
    }

    override fun loadStukLikeNewer(): Flow<Boolean> {
        return stukRepository.getLatestFavoriteStuk().flatMapConcat {
            requireNotNull(it) { "latest stuk its not found, please use loadStukLike" }
            suspend { stukAPI.fetchFavoriteNewer(lastID = it.id) }.asResultFlowThrow()
        }.flatMapConcat { res ->
            flow {
                stukLikeRepository.insert(res.stukLikes.map { StukLikeEntity.fromResponse(it) })
                stukRepository.insertFavorite(res.stuk.map { StukEntity.fromResponse(it) })
                emit(res.isLastPage)
            }
        }
    }
    override fun loadStukLike(): Flow<Boolean> {
        return suspend { stukAPI.fetchFavorite() }.asResultFlowThrow().flatMapConcat { res ->
            flow {
                stukRepository.clearInsertFavorite(res.stuk.map { StukEntity.fromResponse(it) })
                stukLikeRepository.insert(res.stukLikes.map { StukLikeEntity.fromResponse(it) })
                emit(res.isLastPage)
            }
        }
    }

    override fun loadMoreStukLike(): Flow<Boolean> {
        return stukRepository.getOlderFavoriteStuk().flatMapConcat {
            if (it != null) {
                suspend { stukAPI.fetchFavoriteOlder(lastID = it.id) }.asResultFlowThrow()
            } else {
                suspend { stukAPI.fetchFavorite() }.asResultFlowThrow()
            }
        }.flatMapConcat { res ->
            flow {
                stukRepository.insertFavorite(res.stuk.map { StukEntity.fromResponse(it) })
                stukLikeRepository.insert(res.stukLikes.map { StukLikeEntity.fromResponse(it) })
                emit(res.isLastPage)
            }
        }
    }

    override fun likeStuk(stukID: String): Flow<Unit> {
        return suspend { stukAPI.like(stukID) }.asResultFlowThrow().flatMapConcat {
            syncStukSingle(stukID)
        }
    }

    // if true its mean last page, if false its mean not first page
    override fun loadTimelineNewer(): Flow<Boolean> {
        return stukRepository.getLatestTimelineStuk().flatMapConcat {
            requireNotNull(it) { "latest stuk its not found, please use loadTimeline" }
            suspend { stukAPI.fetchTimelineNewer(lastID = it.id) }.asResultFlowThrow()
        }.flatMapConcat { res ->
            flow {
                stukLikeRepository.insert(res.stukLikes.map { StukLikeEntity.fromResponse(it) })
                stukRepository.insertTimeline(res.stuk.map { StukEntity.fromResponse(it) })
                emit(res.isLastPage)
            }
        }
    }

    // This will be remove all the local data and replace with new data
    override fun loadTimeline(): Flow<Boolean> {
        return suspend { stukAPI.fetchTimeline() }.asResultFlowThrow().flatMapConcat { res ->
            flow {
                stukRepository.clearInsertTimeline(res.stuk.map { StukEntity.fromResponse(it) })
                stukLikeRepository.insert(res.stukLikes.map { StukLikeEntity.fromResponse(it) })
                emit(res.isLastPage)
            }
        }
    }

    override fun loadMoreTimeline(): Flow<Boolean> {
        return stukRepository.getOlderTimelineStuk().flatMapConcat {
            if (it != null) {
                suspend { stukAPI.fetchTimelineOlder(lastID = it.id) }.asResultFlowThrow()
            } else {
                suspend { stukAPI.fetchTimeline() }.asResultFlowThrow()
            }
        }.flatMapConcat { res ->
            flow {
                stukRepository.insertTimeline(res.stuk.map { StukEntity.fromResponse(it) })
                stukLikeRepository.insert(res.stukLikes.map { StukLikeEntity.fromResponse(it) })
                emit(res.isLastPage)
            }
        }
    }

    private fun syncStukSingle(stukID: String): Flow<Unit> {
        return suspend { stukAPI.get(stukID) }.asResultFlowThrow()
            .flatMapConcat { res ->
                flow {
                    stukRepository.updateIgnoreType(listOf(StukEntity.fromResponse(res.stuk)))
                }
            }
    }

    override fun searchStuk(query: String, lastID: String): Flow<List<StukUI>> {
        return suspend { stukAPI.search(query, lastID) }.asResultFlowThrow().flatMapConcat {
            liveTimeline(flowOf(it.stuk.map { data -> StukEntity.fromResponse(data) }))
        }
    }

    override fun searchStuk(query: String): Flow<List<StukUI>> {
        return searchStuk(query, "")
    }

    private fun autoUpdateStuk(): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun postStukText(text: String, isAnonymous: Boolean): Flow<Unit> {
        return liveStukConfig().flatMapConcat { config ->
            suspend { stukAPI.createText(isAnonymous, config.isCommentActive, config.isReplyActive, config.isChatActive, text) }.asResultFlowThrow()
        }.flatMapConcat { autoUpdateStuk() }
    }

    override fun postStukImage(text: String, image: List<String>, isAnonymous: Boolean): Flow<Unit> {
        return liveStukConfig().flatMapConcat { config ->
            val imageFile = image.map { File(it) }
            suspend { stukAPI.createImage(isAnonymous, config.isCommentActive, config.isReplyActive, config.isChatActive, text, imageFile) }.asResultFlowThrow()
        }.flatMapConcat { autoUpdateStuk() }
    }

    override fun postStukVoice(text: String, voice: String, isAnonymous: Boolean): Flow<Unit> {
        return liveStukConfig().flatMapConcat { config ->
            val voiceFile = File(voice)
            suspend { stukAPI.createVoice(isAnonymous, config.isCommentActive, config.isReplyActive, config.isChatActive, text, voiceFile) }.asResultFlowThrow()
        }.flatMapConcat { autoUpdateStuk() }
    }

    override fun deleteStuk(stukID: String): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun postStukThread(stukID: String, text: String): Flow<Unit> {
        return liveStukConfig().flatMapConcat { config ->
            val parentStuk = stukRepository.liveById(stukID).firstOrNull()
            requireNotNull(parentStuk) { "parent stuk not found" }
            suspend { stukAPI.createThreadText(parentStuk.isAnonymous, config.isCommentActive, config.isReplyActive, config.isChatActive, text, stukID) }.asResultFlowThrow()
        }.flatMapConcat { autoUpdateStuk() }
    }

    override fun postStukThreadImage(stukID: String, text: String, image: List<String>): Flow<Unit> {
        return liveStukConfig().flatMapConcat { config ->
            val parentStuk = stukRepository.liveById(stukID).firstOrNull()
            requireNotNull(parentStuk) { "parent stuk not found" }
            val imageFile = image.map { File(it) }
            suspend { stukAPI.createThreadImage(parentStuk.isAnonymous, config.isCommentActive, config.isReplyActive, config.isChatActive, text, imageFile, stukID) }.asResultFlowThrow()
        }.flatMapConcat { autoUpdateStuk() }
    }

    override fun postStukThreadVoice(stukID: String, text: String, voice: String): Flow<Unit> {
        return liveStukConfig().flatMapConcat { config ->
            val parentStuk = stukRepository.liveById(stukID).firstOrNull()
            requireNotNull(parentStuk) { "parent stuk not found" }
            val voiceFile = File(voice)
            suspend { stukAPI.createThreadVoice(parentStuk.isAnonymous, config.isCommentActive, config.isReplyActive, config.isChatActive, text, voiceFile, stukID) }.asResultFlowThrow()
        }.flatMapConcat { autoUpdateStuk() }
    }

    override fun deleteStukThread(stukID: String, threadID: String): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun quoteStuk(stukID: String, text: String, isAnonymous: Boolean): Flow<Unit> {
        return liveStukConfig().flatMapConcat { config ->
            val parentStuk = stukRepository.liveById(stukID).firstOrNull()
            requireNotNull(parentStuk) { "parent stuk not found" }
            suspend { stukAPI.quote(parentStuk.isAnonymous, config.isCommentActive, config.isReplyActive, config.isChatActive, text, stukID) }.asResultFlowThrow()
        }.flatMapConcat { autoUpdateStuk() }
    }

    override fun liveFetchComment(stukID: String): Flow<List<CommentUI>> {
        TODO("Not yet implemented")
    }

    override fun liveComment(commentID: String): Flow<CommentUI> {
        TODO("Not yet implemented")
    }

    override fun loadComment(stukID: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun loadMoreComment(stukID: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun likeComment(commentID: String): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun searchComment(query: String, lastID: String): Flow<List<CommentUI>> {
        TODO("Not yet implemented")
    }

    override fun searchComment(query: String): Flow<List<CommentUI>> {
        TODO("Not yet implemented")
    }

    override fun postCommentText(stukID: String, text: String, isAnonymous: Boolean): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteComment(commentID: String): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun loadReply(commentID: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun loadMoreReply(commentID: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun likeReply(replyID: String): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun searchReply(query: String, lastID: String): Flow<List<ReplyUI>> {
        TODO("Not yet implemented")
    }

    override fun searchReply(query: String): Flow<List<ReplyUI>> {
        TODO("Not yet implemented")
    }

    override fun postReplyText(commentID: String, text: String, isAnonymous: Boolean): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteReply(replyID: String): Flow<Unit> {
        TODO("Not yet implemented")
    }

}