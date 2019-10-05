package com.sevenpeakssoftware.redaelhadidy.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *
 * article response is named like this where this application is for car feeds, because it can be scaled
 * to be for other article
 */
data class ArticleResponseEntity(
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("content")
    @Expose
    val articleContentEntity: List<ArticleContentEntity>,
    @SerializedName("serverTime")
    @Expose
    val serverTime: Long
)
