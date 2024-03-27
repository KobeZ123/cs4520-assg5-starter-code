package com.cs4520.assignment5.domain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment5.data.repository.ProductRepository

class FetchProducts(
    private val repository: ProductRepository,
    private val page: Int,
    private val context: Context,
    workerParams: WorkerParameters,
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {

        repository.getProduct(page)
        return Result.success()
    }

}