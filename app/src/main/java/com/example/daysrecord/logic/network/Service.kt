package com.example.daysrecord.logic.network

import com.example.daysrecord.logic.model.Message
import com.example.daysrecord.logic.model.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("message/getAll")
    suspend fun getAllMessages(): Result<Message>

    @POST("message/add")
    suspend fun addMessages(@Body message: Message): Result<Unit>

}