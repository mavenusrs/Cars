package com.sevenpeakssoftware.redaelhadidy.data.entity

import com.google.gson.annotations.Expose

data class ContentEntity(
    @Expose
    val id: Int?,
    @Expose
    val type: String?,
    @Expose
    val subject: String?,
    @Expose
    val description: String?
)