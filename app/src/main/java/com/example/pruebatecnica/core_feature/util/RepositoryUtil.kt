package com.example.pruebatecnica.core_feature.util

import com.example.pruebatecnica.core_feature.data.model.ErrorResponse
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.google.gson.Gson
import retrofit2.Response

class RepositoryUtil {
    companion object{
        suspend fun <T> retrofitCall(
            operation: suspend () -> Response<T>
        ): ResponseState<T> {
            return try {
                val response = operation()
                if (response.isSuccessful) {
                    ResponseState.Success(response.body() ?: throw Exception("Empty response body"))
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                    val errors = errorResponse.errors
                    var errorMessage = ""
                    errors?.forEach { (_, fieldError) ->
                        errorMessage = fieldError.joinToString()
                    }
                    ResponseState.Error(errorMessage,response.code())
                }
            } catch (e: Exception) {
                ResponseState.Error(msg = e.localizedMessage ?: "An unknown error occurred",code = 500)
            }
        }

        // Función genérica de retrofitCall para incluir un mapper
        suspend fun <I, O> retrofitCallWithMapper(
            operation: suspend () -> Response<I>,
            mapper: (I) -> O
        ): ResponseState<O> {
            return try {
                val response = operation()
                if (response.isSuccessful) {
                    val input = response.body() ?: throw Exception("Empty response body")
                    ResponseState.Success(mapper(input))
                } else {
                    when (val code=response.code()) {
                        400 -> ResponseState.Error("Bad Request", code)
                        401 -> ResponseState.Error("Unauthorized", code)
                        404 -> ResponseState.Error("Not Found", code)
                        408 -> ResponseState.Error("Request Timeout", code)
                        500 -> ResponseState.Error("Server Error", code)
                        else -> ResponseState.Error("Unknown Error", code)
                    }
                }
            } catch (e: Exception) {
                ResponseState.Error(e.localizedMessage ?: "An unknown error occurred",204)
            }
        }
    }
}