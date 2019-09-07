package com.sevenpeakssoftware.redaelhadidy.data.remote

import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleResponseEntity
import io.reactivex.Flowable
import retrofit2.http.GET

interface ArticleFeedApi {

    @GET("article/get_articles_list")
    fun getCarsFeed(): Flowable<ArticleResponseEntity>
}