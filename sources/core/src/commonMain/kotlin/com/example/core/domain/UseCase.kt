package com.example.core.domain

import io.github.aakira.napier.Napier

abstract class UseCase<P, R> {

    suspend operator fun invoke(params: P): Result<R> = kotlin.runCatching {
        execute(params)
    }.onFailure {
        Napier.w("Use case ${this::class.simpleName} failed with params: $params", it)
    }

    abstract suspend fun execute(params: P): R
}