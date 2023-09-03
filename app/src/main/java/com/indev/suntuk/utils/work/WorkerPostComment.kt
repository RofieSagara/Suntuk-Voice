package com.indev.suntuk.utils.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.indev.suntuk.service.StukService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class WorkerPostComment(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {

    private lateinit var stukService: StukService
    override suspend fun doWork(): Result {
        val stukID = inputData.getString("stukID").orEmpty()
        val text = inputData.getString("text").orEmpty()
        val isAnonymous = inputData.getBoolean("isAnonymous", false)

        return stukService.postCommentText(stukID, text, isAnonymous)
            .map { Result.success() }
            .catch { emit(Result.failure()) }
            .first()
    }
}