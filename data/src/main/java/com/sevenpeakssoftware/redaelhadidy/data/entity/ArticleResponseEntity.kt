package com.sevenpeakssoftware.redaelhadidy.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *
 * article response is named like this where this appliction is for car feeds, because it can be scaled
 * to be for other article
 */
data class ArticleResponseEntity(
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("articleContent")
    @Expose
    val articleContentEntity: Iterator<ArticleContentEntity>,
    @SerializedName("serverTime")
    @Expose
    val serverTime: Long
)
