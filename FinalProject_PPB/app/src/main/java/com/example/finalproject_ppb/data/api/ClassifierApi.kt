package com.example.finalproject_ppb.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClassifierApi {

    @Multipart
    @POST("classifier")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ResponseModel>

}