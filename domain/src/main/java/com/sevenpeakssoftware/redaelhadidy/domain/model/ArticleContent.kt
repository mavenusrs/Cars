package com.sevenpeakssoftware.redaelhadidy.domain.model

data class ArticleContent(
    var id: Int? = null,
    var title: String? = null,
    var dateTime: String? = null,
    var tags: Iterator<Any>? = null,
    var ingress: String? = null,
    var image: String? = null,
    var created: Int? = null,
    var changed: Int? = null
)