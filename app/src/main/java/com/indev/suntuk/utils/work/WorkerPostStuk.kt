package com.indev.suntuk.utils.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.indev.suntuk.service.StukService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onErrorResume

class WorkerPostStuk(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private lateinit var stukService: StukService

    override suspend fun doWork(): Result {
        val text = inputData.getString("text").orEmpty()
        val isAnonymous = inputData.getBoolean("isAnonymous", false)
        val imagePath = inputData.getStringArray("imagePath")?.toList().orEmpty()
        val voicePath = inputData.getString("voicePath").orEmpty()

        return selectorTypeStuk(text, imagePath, voicePath, isAnonymous)
            .map { Result.success() }
            .catch { emit(Result.failure()) }
            .first()
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