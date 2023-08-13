package com.indev.suntuk.utils.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.indev.suntuk.service.StukService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class WorkerPostStuk(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private lateinit var stukService: StukService

    override suspend fun doWork(): Result {
        val text = inputData.getString("text").orEmpty()
        val isAnonymous = inputData.getBoolean("isAnonymous", false)
        val imagePath = inputData.getStringArray("imagePath")?.toList().orEmpty()
        val voicePath = inputData.getString("voicePath").orEmpty()

        return kotlin.runCatching {
            selectorTypeStuk(text, imagePath, voicePath, isAnonymous)
                .collect()
        }.exceptionOrNull()?.let {
            return Result.failure()
        } ?: Result.success()
    }

    private fun selectorTypeStuk(text: String, imagePath: List<String>, voicePath: String, isAnonymous: Boolean): Flow<Unit> {
        return if (text.isNotBlank() && imagePath.isEmpty() && voicePath.isBlank()) {
            stukService.postStukText(text, isAnonymous)
        } else if (text.isNotBlank() && imagePath.isNotEmpty() && voicePath.isBlank()) {
            stukService.postStukImage(text, imagePath, isAnonymous)
        } else if (text.isNotBlank() && imagePath.isEmpty() && voicePath.isNotBlank()) {
            stukService.postStukVoice(text, voicePath, isAnonymous)
        } else {
            flow { error("can't decide stuk type") }
        }
    }
}