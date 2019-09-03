package com.sevenpeakssoftware.redaelhadidy.domain.usecase.base

import io.reactivex.Flowable

abstract class FlowableUseCase<R> {

    abstract fun execute(): Flowable<R>
}