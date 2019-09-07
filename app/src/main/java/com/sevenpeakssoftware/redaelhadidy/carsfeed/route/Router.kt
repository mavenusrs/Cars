package com.sevenpeakssoftware.redaelhadidy.carsfeed.route

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.sevenpeakssoftware.redaelhadidy.carsfeed.view.MainActivity

const val ARTICLE_DETAIL_EXTRA = "articleDetailExtra"

class Router(private val context: Context) {

    enum class ROUTE {
        ARTICLE_DETAILS, ARTICLE_LIST
    }

    fun navigateTo(route: ROUTE, bundle: Bundle) {
        when (route) {
            ROUTE.ARTICLE_DETAILS -> {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            ROUTE.ARTICLE_LIST -> {
                startActivity(MainActivity::class.java, bundle)
            }
        }
    }

    private fun startActivity(cls: Class<*>, bundle: Bundle) {
        val intent = Intent(context, cls)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}