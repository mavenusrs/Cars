package com.sevenpeakssoftware.redaelhadidy.carsfeed.common

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

@Suppress("UNCHECKED_CAST")
class BehaviorSubjectTrigger<T> {

    private val behaviorSubject = BehaviorSubject.create<T>()
    private val EMPTY = Any()

    fun trigger(value: T){
        if (value == null){
            behaviorSubject.onNext(EMPTY as T)
            return
        }

        behaviorSubject.onNext(value)
    }

    fun observer(): Observable<T> {

        return behaviorSubject.filter {
            it != EMPTY
        }.doOnNext{ clear(it) }

    }

    fun clear(value: T){
        if (value != EMPTY){
            behaviorSubject.onNext(EMPTY as T)
        }
    }
}