package com.sevenpeakssoftware.redaelhadidy.domain.repository

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse

import io.reactivex.Completable
import io.reactivex.Maybe

interface ArticleRepository {
    fun getCarsFeed(): Maybe<ArticleResponse>

    fun getCashedCarsFeed(): Maybe<Iterator<ArticleContent>>
    fun cashedCarsFeed(articleContents: Iterator<ArticleContent>): Completable
}
