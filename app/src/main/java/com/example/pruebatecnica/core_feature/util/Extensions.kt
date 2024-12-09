package com.example.pruebatecnica.core_feature.util

import com.example.pruebatecnica.core_feature.data.model.ResponseState

infix fun <T> ResponseState<T>.takeIfSuccess(onSuccess: ResponseState.Success<T>.() -> Unit): ResponseState<T> {
    return when (this) {
        is ResponseState.Success -> {
            onSuccess(this)
            this
        }
        else -> {
            this
        }
    }
}

infix fun <T> ResponseState<T>.takeIfError(onError: ResponseState.Error.() -> Unit): ResponseState<T> {
    return when (this) {
        is ResponseState.Error -> {
            onError(this)
            this
        }
        else -> {
            this
        }
    }
}