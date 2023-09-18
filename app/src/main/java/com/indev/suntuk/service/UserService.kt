package com.indev.suntuk.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.indev.suntuk.service.api.UserAPI
import com.indev.suntuk.service.api.response.UserBlockResponse
import com.indev.suntuk.service.local.UserAuthorizeRepository
import com.indev.suntuk.service.local.UserBlockRepository
import com.indev.suntuk.service.local.UserCurrencyRepository
import com.indev.suntuk.service.local.UserRepository
import com.indev.suntuk.service.local.entity.user.UserAuthorizeEntity
import com.indev.suntuk.service.local.entity.user.UserBlockEntity
import com.indev.suntuk.service.local.entity.user.UserCurrencyEntity
import com.indev.suntuk.service.local.entity.user.UserEntity
import com.indev.suntuk.service.local.entity.user.fromResponse
import com.indev.suntuk.service.model.User
import com.indev.suntuk.service.model.UserCurrency
import com.indev.suntuk.service.model.fromEntity
import com.indev.suntuk.utils.asResultFlowThrow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface UserService {
    fun registerLogin(): Flow<Unit>
    fun liveUser(): Flow<User?>
    fun liveUserCurrency(): Flow<UserCurrency?>
    fun liveUserStatus(): Flow<User.AccountStatus>
    fun updateProfileNickname(profile: String, nickname: String, isCustomNickname: Boolean): Flow<Unit>
    fun syncUser(): Flow<Unit>
    fun blockUser(targetID: String): Flow<Unit>
    fun unblockUser(targetID: String): Flow<Unit>
    fun isUserBlocked(targetID: String): Flow<Boolean>
    fun syncBlockedUsers(): Flow<Unit>
    fun logout(): Flow<Unit>
}

@OptIn(ExperimentalCoroutinesApi::class)
class IUserService(
    private val userRepository: UserRepository,
    private val userCurrencyRepository: UserCurrencyRepository,
    private val userAuthorizeRepository: UserAuthorizeRepository,
    private val userBlockRepository: UserBlockRepository,
    private val userAPI: UserAPI
): UserService {
    override fun registerLogin(): Flow<Unit> {
        return suspend { userAPI.registerUserIfNotExist() }.asResultFlowThrow()
            .flatMapLatest { syncUser() }
    }

    override fun liveUser(): Flow<User?> {
        return userRepository.liveUser()
            .map { it?.let { userEntity -> User.fromEntity(userEntity) } }
    }

    override fun liveUserCurrency(): Flow<UserCurrency?> {
        return userRepository.liveUserCurrency()
            .map { it?.let { userCurrencyEntity -> UserCurrency.fromEntity(userCurrencyEntity) } }
    }

    override fun liveUserStatus(): Flow<User.AccountStatus> {
        return liveFirebaseUser()
            .map { it?.let { User.AccountStatus.Registered } ?: User.AccountStatus.NotRegistered }
    }

    private fun liveFirebaseUser(): Flow<FirebaseUser?> {
        return callbackFlow {
            val callback = FirebaseAuth.AuthStateListener {
                trySendBlocking(it.currentUser)
            }
            FirebaseAuth.getInstance().addAuthStateListener(callback)

            awaitClose {
                FirebaseAuth.getInstance().removeAuthStateListener(callback)
            }
        }
    }

    private fun liveCurrentUser(): Flow<UserEntity?> {
        return liveFirebaseUser()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(null)
                } else {
                    userRepository.findOneByUID(user.uid)
                }
            }
    }

    override fun updateProfileNickname(
        profile: String,
        nickname: String,
        isCustomNickname: Boolean
    ): Flow<Unit> {
        return suspend {
            userAPI.updateProfileNickname(
                isCustomNickname = isCustomNickname,
                nickname = nickname,
                profile = profile
            )
        }.asResultFlowThrow()
            .flatMapLatest { syncUser() }
    }

    override fun syncUser(): Flow<Unit> {
        return suspend { userAPI.getUser() }.asResultFlowThrow()
            .flatMapLatest { resData ->
                flow {
                    resData.run {
                        userRepository.insert(UserEntity.fromResponse(user))
                        userCurrencyRepository.insert(UserCurrencyEntity.fromResponse(userCurrency))
                        userAuthorizeRepository.insert(UserAuthorizeEntity.fromResponse(userAuthorize))
                    }
                    emit(Unit)
                }
            }
    }

    override fun blockUser(targetID: String): Flow<Unit> {
        return suspend { userAPI.blockUser(targetID) }.asResultFlowThrow()
            .flatMapLatest { syncBlockedUsers() }
    }

    override fun unblockUser(targetID: String): Flow<Unit> {
        return suspend { userAPI.unBlockUser(targetID) }.asResultFlowThrow()
            .flatMapLatest { syncBlockedUsers() }
    }

    override fun isUserBlocked(targetID: String): Flow<Boolean> {
        return liveCurrentUser().flatMapLatest { userEntity ->
            if (userEntity == null) {
                flowOf(false)
            } else {
                userBlockRepository.findOne(userEntity.uid, targetID)
                    .map { it != null }
            }
        }
    }

    override fun syncBlockedUsers(): Flow<Unit> {
        return suspend { userAPI.fetchBlockUser() }.asResultFlowThrow()
            .map { res ->
                mutableListOf<UserBlockResponse>().apply {
                    addAll(res.blockUsers)
                    addAll(res.blockedBy)
                }
            }.flatMapLatest { usersBlock ->
                flow {
                    userBlockRepository.insert(usersBlock.map { UserBlockEntity.fromResponse(it) })
                    emit(Unit)
                }
            }
    }

    override fun logout(): Flow<Unit> {
        return suspend { userRepository.clear() }.asFlow()
    }
}