package com.sevenpeakssoftware.redaelhadidy.domain.model

import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException

sealed class ResultState<out T: Any> {
    data class Success<T : Any>(val data: T) : ResultState<T>()
    data class Failed<T : Any>(val exception: ArticleException, val cashedData: T? = null) : ResultState<T>()
    object Loading : ResultState<Nothing>()
}