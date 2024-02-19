package com.example.currencyexchanger.data.storege.impl

import android.content.Context
import androidx.core.content.edit
import com.example.currencyexchanger.data.storege.ExchangeCounterStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

private const val EXCHANGE_PREFERENCE_KEY = "EXCHANGE_PREFERENCE"
private const val EXCHANGE_COUNTER_KEY = "COUNTER_KEY"

internal class ExchangeCounterStorageImpl(private val context: Context) : ExchangeCounterStorage {

    private val sharedPreferences = context
        .getSharedPreferences(EXCHANGE_PREFERENCE_KEY, Context.MODE_PRIVATE)

    private val _countFlow = MutableStateFlow(getCount())
    override val countFlow: Flow<Int> get() = _countFlow

    override fun getCount(): Int = sharedPreferences
        .getInt(EXCHANGE_COUNTER_KEY, 0)

    override fun addExchange() {
        val currentCount = getCount() + 1
        sharedPreferences.edit() {
            putInt(EXCHANGE_COUNTER_KEY, currentCount)
            apply()
        }
        _countFlow.value = currentCount
    }
}
