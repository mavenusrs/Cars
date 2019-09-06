package com.sevenpeakssoftware.redaelhadidy.carsfeed.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addsTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}