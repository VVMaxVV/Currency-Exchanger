package com.example.currencyexchanger.domain.repository

import com.example.currencyexchanger.domain.model.RateCurrency

interface RateRepository {
    suspend fun getRateCurrencyList(): List<RateCurrency>
    suspend fun getCurrencyCodeList(): List<String>
}
