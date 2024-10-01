package com.example.emircanproject

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Arka planda bir işlem gerçekleştir
        delay(2000) // İşlemi simüle etmek için 2 saniye beklet
        logMessage("Background operation completed")
        return Result.success()
    }

    private fun logMessage(message: String) {
        // Log mesajını yazdır
        // Burada gerçek bir loglama işlemi yapılabilir
        println(message)
    }
}
