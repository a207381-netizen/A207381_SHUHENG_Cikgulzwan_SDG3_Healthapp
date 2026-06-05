package com.example.a207381_cikguizwan_lab01

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HealthApiService {

    @GET("v3/covid-19/all")
    suspend fun getGlobalHealthStats(): HealthApiResponse
}

object HealthApiClient {

    val apiService: HealthApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://disease.sh/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HealthApiService::class.java)
    }
}
