package com.luscii.techassignment.data.misc

import java.io.IOException

@Suppress("All")
sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body.
     */
    data class Success<T : Any>(val body: T, val headers: Map<String, String> = emptyMap()) :
        NetworkResponse<T, Nothing>()

    /**
     * Failure response with body.
     */
    data class ApiError<U : Any>(
        val body: U,
        val code: Int,
        val headers: Map<String, String> = emptyMap()
    ) :
        NetworkResponse<Nothing, U>()

    /**
     * Network error.
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error.
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()

    /**
     * Returns 404.
     */
    object NotFound : NetworkResponse<Nothing, Nothing>()

    /**
     * Returns 304.
     */
    object NotModified : NetworkResponse<Nothing, Nothing>()

    object EmptyBody : NetworkResponse<Nothing, Nothing>()
}
