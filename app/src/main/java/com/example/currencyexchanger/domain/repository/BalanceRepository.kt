package com.example.currencyexchanger.domain.repository

import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.model.CurrencyExchange
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    suspend fun getBalance(): Flow<List<Currency>?>
    suspend fun updateBalance(
        selledCurrency: CurrencyExchange,
        receivedCurrency: CurrencyExchange
    )

    suspend fun getBalance(currencyCode: String): Currency

    suspend fun giveStartingBonus()
}
