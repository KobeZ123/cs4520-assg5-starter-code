package com.cs4520.assignment5.domain

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment5.util.constants.Api
import com.cs4520.assignment5.data.api.ProductApi
import com.cs4520.assignment5.data.api.ProductClient
import com.cs4520.assignment5.data.local.ProductDatabase
import com.cs4520.assignment5.data.models.Product
import com.cs4520.assignment5.data.repository.ProductRepository
import com.cs4520.assignment5.data.repository.ProductRepositoryImpl
import com.cs4520.assignment5.domain.worker.FetchProducts
import com.cs4520.assignment5.util.filterList
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

class ProductListViewModel(
    private val application: Application
) : AndroidViewModel(application) {


    private val retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    private val productClient: ProductClient = ProductClient(productApi)

    private val db = Room.databaseBuilder(
        application.applicationContext,
        ProductDatabase::class.java,
        "product_table"
    ).build()

    private val productDao = db.productDao()

    private val repository: ProductRepository = ProductRepositoryImpl(productClient, productDao)

    var isLoading: MutableState<Boolean> = mutableStateOf(true)

    var productListState: MutableState<List<Product>?> = mutableStateOf(null)

    var errorMessageState: MutableState<String?> = mutableStateOf(null)

    var pageNumberState: MutableState<Int> = mutableStateOf(1)

    init {
        viewModelScope.launch {
            fetchProducts(pageNumberState.value)
        }
    }

    /**
     * fetch products from the repository using coroutine
     * if successful, post product live to live data and add to room database
     * if unsuccessful, display error message
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchProducts(page: Int) {
        isLoading.value = true
        productListState.value = null
        viewModelScope.launch {
            // set up the work manager
            val work = PeriodicWorkRequestBuilder<FetchProducts>(repeatInterval= Duration.ofHours(1)).build()
            val result = WorkManager.getInstance(application.applicationContext).enqueue(work)

            try {
                val response = repository.getProduct(page)

                response?.let {
                    productListState.value = it.filterList()
                } ?: run {
                    errorMessageState.value = "Random error occurred in API response"
                }
            } catch (e: Exception){
                errorMessageState.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onNextPageClick() {
        pageNumberState.value += 1
    }

    fun onBackPageClick() {
        if (pageNumberState.value > 1) {
            pageNumberState.value -= 1
        }
    }
}