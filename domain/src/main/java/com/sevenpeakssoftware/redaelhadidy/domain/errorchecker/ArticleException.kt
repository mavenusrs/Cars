package com.sevenpeakssoftware.redaelhadidy.domain.errorchecker

import com.sevenpeakssoftware.redaelhadidy.domain.common.SERVER_GENERAL_ERROR
import java.lang.Exception

data class ArticleException(
    private val errorCause: Int = SERVER_GENERAL_ERROR,
    private val throwable: Throwable
) : Exception(throwable)