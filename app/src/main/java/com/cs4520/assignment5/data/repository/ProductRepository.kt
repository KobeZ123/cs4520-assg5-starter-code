package com.cs4520.assignment5.data.repository

import com.cs4520.assignment5.data.models.Product

/**
 * This repository fetches product information.
 */
interface ProductRepository {
    suspend fun getProduct(page: Int): List<Product>?
}