package com.example.pruebatecnica.core_feature.data.model

sealed class ResponseState <out T> {
    data class Success<T>(val data:T):ResponseState<T>()
    data class Error(val msg: String?,val code:Int?):ResponseState<Nothing>()
}