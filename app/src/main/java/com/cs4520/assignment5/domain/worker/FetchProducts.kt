package com.cs4520.assignment5.domain.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment5.data.api.ProductClient
import com.cs4520.assignment5.data.local.ProductDatabase
import com.cs4520.assignment5.data.repository.ProductRepository
import com.cs4520.assignment5.data.repository.ProductRepositoryImpl

class FetchProducts(
    private val context: Context,
    private val workerParams: WorkerParameters,
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.e("[KOBE] using worker", "using worker")

        val productClient: ProductClient = ProductClient.getInstance()
        val productDatabase: ProductDatabase = ProductDatabase.getInstance(context)
        val repository: ProductRepository = ProductRepositoryImpl(productClient, productDatabase.productDao())
        repository.getProduct(workerParams.inputData.getInt("page", 1))
        return Result.success()
    }
}