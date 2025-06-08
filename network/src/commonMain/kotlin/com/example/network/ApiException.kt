package com.example.network

import com.example.ExceptionResponse

class ApiException (val exceptionBody: ExceptionResponse): RuntimeException(exceptionBody.message)