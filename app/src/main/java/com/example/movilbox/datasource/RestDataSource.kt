package com.example.movilbox.datasource

import com.example.movilbox.model.ApiResponse
import retrofit2.http.GET

interface RestDataSource {

    @GET("products")
    suspend fun getProducts(): ApiResponse

    @GET("products/categories")
    suspend fun getCategories(): List<String>
}