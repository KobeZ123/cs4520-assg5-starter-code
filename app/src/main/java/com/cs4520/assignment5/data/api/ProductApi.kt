package com.cs4520.assignment5.data.api

import com.cs4520.assignment5.util.constants.Api
import com.cs4520.assignment5.data.models.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET(Api.ENDPOINT)
    suspend fun getProducts(@Query("page") page: Int?): Response<List<ProductDto>>
}