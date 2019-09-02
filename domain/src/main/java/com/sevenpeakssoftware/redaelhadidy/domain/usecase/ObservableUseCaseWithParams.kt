package com.sevenpeakssoftware.redaelhadidy.domain.usecase

import io.reactivex.Observable

abstract class ObservableUseCaseWithParams<P, R> {

    abstract fun execute(param: P): Observable<R>
}