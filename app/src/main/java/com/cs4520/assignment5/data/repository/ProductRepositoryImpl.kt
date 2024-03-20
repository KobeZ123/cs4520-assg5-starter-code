package com.cs4520.assignment5.data.repository

import android.util.Log
import com.cs4520.assignment5.data.api.ProductClient
import com.cs4520.assignment5.data.models.toProduct
import com.cs4520.assignment5.data.local.ProductDao
import com.cs4520.assignment5.data.models.Product

/**
 * implementation of the product repository to fetch product information
 */
class ProductRepositoryImpl(
    private val apiClient: ProductClient,
    private val productDao: ProductDao
) : ProductRepository {

    /**
     * attempts to make api request and returns the product list if request is successful
     * else gets product list from the local room database
     */
    override suspend fun getProduct(page: Int): List<Product>? {
        return try {
            val response = apiClient.getProducts(page)
            if (response.isSuccessful) {
                Log.e("[API] SUCCESS", response.body().toString())
                val productListFromApi = response.body()
                if (!productListFromApi.isNullOrEmpty()) {
                    Log.e("[ROOM] ADDING TO DATABASE", productListFromApi.toString())
                    productDao.deleteAll()
                    productDao.addAllProducts(productListFromApi)
                }
                productListFromApi?.map{ productDto -> productDto.toProduct() }
            } else {
                Log.e("[API] UNSUCCESSFUL", response.message())

                getProductsFromDatabase()
            }
        } catch (e: Exception) {
            Log.e("[API] EXCEPTION", e.message.toString())

            getProductsFromDatabase()
        }
    }

    /**
     * Gets the list of products from the database
     */
    private suspend fun getProductsFromDatabase() : List<Product> {
        val databaseProductList = productDao.getAllProducts()
        Log.e("[ROOM] LOADING FROM DATABASE", databaseProductList.toString())

        return databaseProductList.map{
            it.toProduct()
        }
    }
}