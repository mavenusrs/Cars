package com.sevenpeakssoftware.redaelhadidy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevenpeakssoftware.redaelhadidy.data.DATABASE_VERSION
import com.sevenpeakssoftware.redaelhadidy.data.entity.ArticleContentEntity

@Database(entities = [ArticleContentEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class ArticleFeedDatabase: RoomDatabase() {
    abstract fun articleDAO(): ArticleFeedDAO
}