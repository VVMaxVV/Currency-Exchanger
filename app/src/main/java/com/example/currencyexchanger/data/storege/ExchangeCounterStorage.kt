package com.example.currencyexchanger.data.storege

import kotlinx.coroutines.flow.Flow

internal interface ExchangeCounterStorage {
    fun getCount(): Int
    fun addExchange()
    val countFlow: Flow<Int>
}
