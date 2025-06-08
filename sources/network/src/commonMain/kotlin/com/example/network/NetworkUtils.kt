package com.example.network

import com.example.ExceptionResponse
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
    if(status.isSuccess()){
        return body()
    }else{
        val errorBody: ExceptionResponse = body()
        throw ApiException(errorBody)
    }
}