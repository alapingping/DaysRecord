package com.example.daysrecord.logic.network

import com.example.daysrecord.logic.model.Message
import com.example.daysrecord.logic.model.Result
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface Service {

    @GET("message/getAll")
    suspend fun getAllMessages(): Result<Message>

}