package com.sevenpeakssoftware.redaelhadidy.carsfeed.model

/**
 * Two things to be noticed her,
 * The first thing is I set this model with minimal fields, may be on the future we could
 * expand it, two, It is  a parcelable class because we could scale application to pass this model
 * to other activity/fragment
 */
import android.os.Parcel
import android.os.Parcelable

data class ArticleContentParcelable(
    private val id: Int,
    private val title: String?,
    private val dateTime: String?,
    private val ingress: String?,
    private val image: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.readInt()
        parcel.writeString(title)
        parcel.writeString(dateTime)
        parcel.writeString(ingress)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleContentParcelable> {
        override fun createFromParcel(parcel: Parcel): ArticleContentParcelable {
            return ArticleContentParcelable(parcel)
        }

        override fun newArray(size: Int): Array<ArticleContentParcelable?> {
            return arrayOfNulls(size)
        }
    }
}