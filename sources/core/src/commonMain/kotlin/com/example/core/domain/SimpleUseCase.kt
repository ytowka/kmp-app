package com.example.core.domain

import io.github.aakira.napier.Napier

abstract class SimpleUseCase<R> {

    suspend operator fun invoke(): Result<R> = kotlin.runCatching {
        execute()
    }.onFailure {
        Napier.w("Use case ${this::class.simpleName} failed", it)
    }

    abstract suspend fun execute(): R
}