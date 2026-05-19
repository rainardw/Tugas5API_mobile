package com.example.patientapiapp.network

import com.example.patientapiapp.model.ApiResponse
import com.example.patientapiapp.model.LoginRequest
import com.example.patientapiapp.model.LoginResponse
import com.example.patientapiapp.model.Patient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("pasien")
    suspend fun getPasien(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<Patient>>>
}