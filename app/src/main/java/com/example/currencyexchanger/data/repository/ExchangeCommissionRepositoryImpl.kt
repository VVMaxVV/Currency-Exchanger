package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.storege.ExchangeCounterStorage
import com.example.currencyexchanger.domain.model.CurrencyExchange
import com.example.currencyexchanger.domain.repository.ExchangeCommissionRepository
import kotlinx.coroutines.flow.map

private const val EXCHANGE_COMMISSION = 0.007 // 0.7%
private const val MIN_AMOUNT_WITHOUT_COMMISSION = 200
private const val FREE_TRANSACTION_FREQUENCY = 5

internal class ExchangeCommissionRepositoryImpl(
    private val exchangeCounterStorage: ExchangeCounterStorage
) : ExchangeCommissionRepository {

    override suspend fun getExchangeCommissions(currency: CurrencyExchange): Double {
        if (currency.amount.times(currency.rate) > MIN_AMOUNT_WITHOUT_COMMISSION) return 0.0
        if ((exchangeCounterStorage.getCount() + 1) % FREE_TRANSACTION_FREQUENCY == 0) return 0.0
        return currency.amount.times(EXCHANGE_COMMISSION)
    }

    override suspend fun getNumberTransactionsBeforeFreeCommissions() =
        exchangeCounterStorage.countFlow.map {
            FREE_TRANSACTION_FREQUENCY - (it % FREE_TRANSACTION_FREQUENCY) - 1
        }
}
