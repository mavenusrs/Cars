package com.sevenpeakssoftware.redaelhadidy.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArticleContentEntity(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("dateTime")
    @Expose
    val dateTime: String,
    @SerializedName("tags")
    @Expose
    val tags: Iterator<Any>?,
    @SerializedName("content")
    @Expose
    val contents: Iterator<ContentEntity>?,
    @SerializedName("ingress")
    @Expose
    val ingress: String,
    @SerializedName("image")
    @Expose
    val image: String,
    @SerializedName("created")
    @Expose
    val created: Int,
    @SerializedName("changed")
    @Expose
    val changed: Int
)