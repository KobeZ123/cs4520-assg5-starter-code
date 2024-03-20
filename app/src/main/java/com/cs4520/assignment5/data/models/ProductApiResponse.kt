package com.cs4520.assignment5.data.models

import com.google.gson.annotations.SerializedName

class ProductApiResponse {
    @SerializedName("message")
    private val message: String? = null

    @SerializedName("data")
    private val data: List<Product>? = null

    fun getMessage(): String? {
        return message
    }

    fun getData(): List<Product>? {
        return data
    }
}