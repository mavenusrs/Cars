package com.sevenpeakssoftware.redaelhadidy.domain.repository

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse

import io.reactivex.Completable
import io.reactivex.Flowable

interface ArticleRepository {
    fun getCarsFeed(): Flowable<ArticleResponse>

    fun getCashedCarsFeed(): Flowable<Iterator<ArticleContent>>
    fun cashedCarsFeed(articleContents: Iterator<ArticleContent>): Completable
}
