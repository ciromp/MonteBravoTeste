package com.ciro.montebravotest.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun client() : OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

fun retrofit() : Retrofit =
    Retrofit.Builder()
        .baseUrl("https://api.montebravo.lionx.ai/")
        .client(client())
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .build()

fun retrofit2() : Retrofit =
    Retrofit.Builder()
        .baseUrl("https://www.alphavantage.co/")
        .client(client())
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType())
        ).build()

fun services() : ApiService =
    retrofit().create(ApiService::class.java)

fun servicesChart() : ApiService =
    retrofit2().create(ApiService::class.java)