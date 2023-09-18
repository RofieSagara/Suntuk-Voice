package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.stuk.StukLikeEntity
import kotlinx.coroutines.flow.Flow

interface StukLikeRepository {
    fun liveFetchUserLikeByStukID(stukIDCollection: List<String>): Flow<List<StukLikeEntity>>
    suspend fun insert(data: List<StukLikeEntity>)
}