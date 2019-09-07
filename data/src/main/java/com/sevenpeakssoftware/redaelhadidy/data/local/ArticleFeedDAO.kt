package com.sevenpeakssoftware.redaelhadidy.data.local

import androidx.room.*
import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleContentEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ArticleFeedDAO {

    @Query("SELECT * FROM article_tbl")
    fun getCarsFeed(): Single<List<ArticleContentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarsFeeds(articleContentEntities: List<ArticleContentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarsFeed(articleContentEntities: ArticleContentEntity)

    @Query("DELETE FROM article_tbl")
    fun clearCarsFeed()

}