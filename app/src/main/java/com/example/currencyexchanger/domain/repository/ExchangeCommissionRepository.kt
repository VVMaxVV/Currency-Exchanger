package com.example.currencyexchanger.domain.repository

import com.example.currencyexchanger.domain.model.CurrencyExchange
import kotlinx.coroutines.flow.Flow

interface ExchangeCommissionRepository {
    suspend fun getExchangeCommissions(currency: CurrencyExchange): Double

    suspend fun getNumberTransactionsBeforeFreeCommissions(): Flow<Int>
}
