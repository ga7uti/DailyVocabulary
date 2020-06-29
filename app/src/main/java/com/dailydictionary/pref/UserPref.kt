package com.dailydictionary.pref

import android.content.Context

class UserPref(context: Context) {


    companion object {
        private const val DARK_STATUS = "DARK_STATUS"
    }

    private val preferences = context.getSharedPreferences(DARK_STATUS, Context.MODE_PRIVATE)


    var darkMode = preferences.getInt(DARK_STATUS, -1)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
}