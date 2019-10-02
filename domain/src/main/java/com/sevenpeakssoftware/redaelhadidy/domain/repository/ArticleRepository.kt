package com.sevenpeakssoftware.redaelhadidy.domain.repository

import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleContent
import com.sevenpeakssoftware.redaelhadidy.domain.model.ArticleResponse

import io.reactivex.Single

interface ArticleRepository {
    fun getCarsFeed(): Single<ArticleResponse>

    fun getCashedCarsFeed(): Single<List<ArticleContent>>
    fun cashedCarsFeed(articleContents: List<ArticleContent>)
}
