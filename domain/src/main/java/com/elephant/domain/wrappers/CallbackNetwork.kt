package com.elephant.domain.wrappers

sealed class CallbackNetwork<out T, out E> {
    data class Success<T>(val response: T) : CallbackNetwork<T, Nothing>()
    data class Failure<E>(val reason: E) : CallbackNetwork<Nothing, E>()
}