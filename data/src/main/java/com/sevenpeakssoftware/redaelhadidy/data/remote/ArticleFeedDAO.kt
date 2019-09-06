package com.sevenpeakssoftware.redaelhadidy.data.remote

import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleContentEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ArticleFeedDAO {
    fun getCarsFeed(): Flowable<Iterator<ArticleContentEntity>>
    fun insertCarsFeed(articleContentEntities :Iterator<ArticleContentEntity>): Completable
    fun clearCarsFeed(): Completable

}