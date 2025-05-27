package com.example.a51762_work3.api

import com.example.a51762_work3.model.Brand
import com.example.a51762_work3.model.ModelInfo
import com.example.a51762_work3.model.ModelsResponse
import com.example.a51762_work3.model.Year
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .baseUrl("https://parallelum.com.br/fipe/api/v1/carros/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val carService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("marcas")
    suspend fun getBrands(): List<Brand>

    @GET("marcas/{brandId}/modelos")
    suspend fun getModels(
        @Path("brandId") brandId: Int
    ): ModelsResponse

    @GET("marcas/{brandId}/modelos/{modelId}/anos")
    suspend fun getYears(
        @Path("brandId") brandId: Int,
        @Path("modelId") modelId: Int
    ): List<Year>

    @GET("marcas/{brandId}/modelos/{modelId}/anos/{yearId}")
    suspend fun getModelsInfo(
        @Path("brandId") brandId: Int,
        @Path("modelId") modelId: Int,
        @Path("yearId") yearId: String
    ): ModelInfo
}