package com.sevenpeakssoftware.redaelhadidy.carsfeed.model

/**
 * Two things to be noticed her,
 * The first thing is I set this model with minimal fields, may be on the future we could
 * expand it, two, It is  a parcelable class because we could scale application to pass this model
 * to other activity/fragment
 */
import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class ArticleContentParcelable(
     val id: Int?,
     val title: String?,
     val dateTime: Long?,
     val ingress: String?,
     val image: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.readInt()
        parcel.writeString(title?:"")
        parcel.writeLong(dateTime?:Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis)
        parcel.writeString(ingress?:"")
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Id: $id, Title: $title, DataTime: $dateTime, Ingress: $ingress, ImageURL, $image"
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