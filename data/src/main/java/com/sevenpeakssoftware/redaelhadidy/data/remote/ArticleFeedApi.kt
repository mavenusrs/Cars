package com.sevenpeakssoftware.redaelhadidy.data.remote

import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleResponseEntity
import io.reactivex.Single
import retrofit2.http.GET

interface ArticleFeedApi {

    @GET("article/get_articles_list")
    fun getCarsFeed(): Single<ArticleResponseEntity>
}