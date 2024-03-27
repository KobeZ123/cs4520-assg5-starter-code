package com.cs4520.assignment5.data.api

import com.cs4520.assignment5.data.models.ProductDto
import com.cs4520.assignment5.util.constants.Api
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductClient(
    private val productApi: ProductApi
) {
    suspend fun getProducts(page: Int): Response<List<ProductDto>> {
        return productApi.getProducts(page)
    }

    companion object {
        @Volatile
        private var INSTANCE: ProductClient? = null

        fun getInstance(): ProductClient {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val productApi: ProductApi = retrofit.create(ProductApi::class.java)

                    instance = ProductClient(productApi)

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}