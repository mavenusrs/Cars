package com.sevenpeakssoftware.redaelhadidy.carsfeed.common

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

@Suppress("UNCHECKED_CAST")
class BehaviorSubjectTrigger<T> {

    private val behaviorSubject = BehaviorSubject.create<T>()
    private val empty = Any()

    fun trigger(value: T){
        if (value == null){
            behaviorSubject.onNext(empty as T)
            return
        }

        behaviorSubject.onNext(value)
    }

    fun observer(): Observable<T> {

        return behaviorSubject.filter {
            it != empty
        }.doOnNext{ clear(it) }

    }

    private fun clear(value: T){
        if (value != empty){
            behaviorSubject.onNext(empty as T)
        }
    }
}