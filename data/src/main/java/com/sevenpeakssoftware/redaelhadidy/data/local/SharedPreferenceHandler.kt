package com.sevenpeakssoftware.redaelhadidy.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sevenpeakssoftware.redaelhadidy.data.SHARED_PREF_NAME

class SharedPreferenceHandler(context: Context) {

    private val gson = Gson()
    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun save(key: String, value: Any, classType: TypeToken<Long>) {
        val json = gson.toJson(value, classType.rawType)
        sharedPreference.edit().putString(key, json).apply()
    }

    fun get(key: String, alternative: Any?, classType: TypeToken<out Any>): Any? {
        return if (sharedPreference.contains(key)) {
            var json = ""
            alternative?.apply { json = gson.toJson(alternative, classType.type) }

            val value = sharedPreference.getString(key, json)
            gson.fromJson(value, classType.rawType)
        } else {
            return alternative
        }
    }

    fun remove(key: String) {
        sharedPreference.edit().remove(key).apply()
    }
}