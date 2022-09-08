package com.elephant.domain.usecase

import kotlinx.coroutines.flow.Flow

typealias SearchQueryJSONUseCase<T, E> = (date: String) -> Flow<CaseResult<T, E>>
typealias SearchQueryPicturesUseCase<T, E> = (url: String) -> Flow<CaseResult<T, E>>

sealed class CaseResult<out T, out E> {
    data class Success<T>(val response: T) : CaseResult<T, Nothing>()
    data class Failure<E>(val reason: E) : CaseResult<Nothing, E>()
}
//TODO зачем он нужен?