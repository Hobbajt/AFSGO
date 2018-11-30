package com.hobbajt.afsgo.data

import android.content.SharedPreferences
import javax.inject.Inject

class LocalPropertiesEditor @Inject constructor(private val sharedPreferences: SharedPreferences)
{
    companion object
    {
        private const val IS_FIRST_RUN_TAG = "isFirstRun"
    }

    fun readIsFirstRun() = sharedPreferences.getBoolean(IS_FIRST_RUN_TAG, true)

    fun writeNotFirstRun() = sharedPreferences.edit().putBoolean(IS_FIRST_RUN_TAG, false).apply()
}
