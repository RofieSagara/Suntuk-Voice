package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.stuk.StukEntity
import kotlinx.coroutines.flow.Flow

interface StukRepository {
    fun liveFetchTimeline(): Flow<List<StukEntity>>
    fun liveFetchOwner(): Flow<List<StukEntity>>
    fun liveFetchFavorite(): Flow<List<StukEntity>>
    fun liveFetchByIds(ids: List<String>): Flow<List<StukEntity>>
    fun liveById(id: String): Flow<StukEntity?>
    suspend fun clearInsertTimeline(data: List<StukEntity>)
    suspend fun clearInsertOwner(data: List<StukEntity>)
    suspend fun clearInsertFavorite(data: List<StukEntity>)
    fun getLatestTimelineStuk(): Flow<StukEntity?>
    fun getOlderTimelineStuk(): Flow<StukEntity?>
    fun getLatestOwnerStuk(): Flow<StukEntity?>
    fun getOlderOwnerStuk(): Flow<StukEntity?>
    fun getLatestFavoriteStuk(): Flow<StukEntity?>
    fun getOlderFavoriteStuk(): Flow<StukEntity?>
    suspend fun insertTimeline(data: List<StukEntity>)
    suspend fun insertOwner(data: List<StukEntity>)
    suspend fun insertFavorite(data: List<StukEntity>)
    suspend fun updateIgnoreType(data: List<StukEntity>) // this will update and ignore the type isTimeline or isOwner or isFavorite
}