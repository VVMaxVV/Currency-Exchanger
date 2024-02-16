package com.example.currencyexchanger.domain.repository

import com.example.currencyexchanger.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    suspend fun getBalance(): Flow<List<Currency>?>
    suspend fun updateBalance(updatedCurrencyList: List<Currency>)
}
