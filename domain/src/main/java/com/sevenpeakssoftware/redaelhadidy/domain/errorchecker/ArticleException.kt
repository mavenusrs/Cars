package com.sevenpeakssoftware.redaelhadidy.domain.errorchecker

import java.lang.Exception

data class ArticleException(private val errorCause: Int?, private val errorMessage: String?): Exception(errorMessage)