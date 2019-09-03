package com.sevenpeakssoftware.redaelhadidy.domain.model

import java.lang.Exception

sealed class ResultState<T> {
    data class Loading<T>(val data: T?) : ResultState<T>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Failed<T>(val exception: Exception, val cashedData: T?) : ResultState<T>()
}