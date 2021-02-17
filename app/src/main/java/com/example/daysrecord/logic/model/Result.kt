package com.example.daysrecord.logic.model

data class Result<T>(val code: Int, val info: String, val data: List<T>)
