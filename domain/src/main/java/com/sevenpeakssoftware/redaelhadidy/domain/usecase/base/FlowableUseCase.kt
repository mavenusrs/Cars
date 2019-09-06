package com.sevenpeakssoftware.redaelhadidy.domain.usecase.base

import com.sevenpeakssoftware.redaelhadidy.domain.common.HTTP_EXCEPTION_ERROR
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException
import com.sevenpeakssoftware.redaelhadidy.domain.model.ResultState
import io.reactivex.Flowable
import retrofit2.HttpException

abstract class FlowableUseCase<R : Any> {

    fun execute(): Flowable<ResultState<R>> {
        return run().map {
            ResultState.Success(it) as ResultState<R>
        }.onErrorReturn {
            when (it) {
                is HttpException -> ResultState.Failed(
                    ArticleException(
                        HTTP_EXCEPTION_ERROR,
                        it
                    ), null
                )
                else -> ResultState.Failed(ArticleException(throwable = it), null)
            }
        }
    }

    abstract fun run(): Flowable<R>
}