package com.example.currencyexchanger.domain.repository

import com.example.currencyexchanger.domain.model.Currency

interface BalanceRepository {
    suspend fun getBalance(): List<Currency>
}
