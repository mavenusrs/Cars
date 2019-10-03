package com.sevenpeakssoftware.redaelhadidy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article_tbl")
data class ArticleContentEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "myId")
    @SerializedName("myId")
    @Expose
    var myIid: Int? = null,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int?,
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String,
    @SerializedName("dateTime")
    @Expose
    @ColumnInfo(name = "dateTime")
    var dateTime: String,
    @SerializedName("tags")
    @Expose
    @Ignore
    var tags: List<Any>?,
    @SerializedName("content")
    @Expose
    @Ignore
    var contents: List<ContentEntity>?,
    @SerializedName("ingress")
    @Expose
    @ColumnInfo(name = "ingress")
    var ingress: String,
    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    var image: String,
    @SerializedName("created")
    @Expose
    @Ignore
    var created: Long,
    @SerializedName("changed")
    @Expose
    @Ignore
    var changed: Long
){
    constructor():this(null, null,"", "", null, null, "", "", 0, 0)
}
