package com.example.currencyexchanger.data.storege.impl

import android.content.Context
import com.example.currencyexchanger.data.storege.FirstRunStorage

private const val FIRST_RUN_PREFERENCE_KEY = "FIRS_RUN_PREFERENCE"
private const val FIRST_RUN_KEY = "FIRS_RUN_KEY"

internal class FirstRunStorageImpl(context: Context) : FirstRunStorage {
    private val sharedPreferences =
        context.getSharedPreferences(FIRST_RUN_PREFERENCE_KEY, Context.MODE_PRIVATE)

    override fun isFirstRun(): Boolean {
        sharedPreferences.getBoolean(FIRST_RUN_KEY, true).also {
            return if (it) {
                sharedPreferences.edit().putBoolean(FIRST_RUN_KEY, false).apply()
                true
            } else {
                false
            }
        }
    }
}
