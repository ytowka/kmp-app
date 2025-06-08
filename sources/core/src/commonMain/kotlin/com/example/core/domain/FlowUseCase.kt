package com.example.core.domain

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class FlowUseCase<P, R> {

    operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .map {
            Result.success(it)
        }.catch {
            Napier.w("Use case ${this::class.simpleName} failing with params: $params", it)
            emit(Result.failure(it))
        }

    abstract fun execute(params: P): Flow<R>
}