package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.dao.BalanceDAO
import com.example.currencyexchanger.data.model.CurrencyEntity
import com.example.currencyexchanger.data.model.ExchangeTransaction
import com.example.currencyexchanger.data.storege.ExchangeCounterStorage
import com.example.currencyexchanger.data.storege.FirstRunStorage
import com.example.currencyexchanger.domain.execption.ExchangeAmountIsZeroOrLessException
import com.example.currencyexchanger.domain.execption.IllegalBalanceState
import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.model.CurrencyExchange
import com.example.currencyexchanger.domain.repository.BalanceRepository
import com.example.currencyexchanger.domain.repository.ExchangeCommissionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class BalanceRepositoryImpl(
    private val balanceDao: BalanceDAO,
    private val exchangeCounterStorage: ExchangeCounterStorage,
    private val firstRunStorage: FirstRunStorage,
    private val exchangeCommissionRepository: ExchangeCommissionRepository
) : BalanceRepository {
    override suspend fun getBalance(): Flow<List<Currency>?> =
        balanceDao.getBalance().map {
            it.map { Currency(it.code, it.amount) }
        }

    override suspend fun updateBalance(
        selledCurrency: CurrencyExchange,
        receivedCurrency: CurrencyExchange
    ) {
        val selledCurrencyBalance = getBalance(selledCurrency.currencyCode)
        val receivedCurrencyBalance = getBalance(receivedCurrency.currencyCode)

        val commission = exchangeCommissionRepository.getExchangeCommissions(selledCurrency)

        if (selledCurrencyBalance.amount - selledCurrency.amount - commission < 0.0) {
            throw IllegalBalanceState()
        }

        if (selledCurrencyBalance.amount <= 0.0 || receivedCurrency.amount <= 0.0) {
            throw ExchangeAmountIsZeroOrLessException()
        }

        balanceDao.applyTransaction(
            ExchangeTransaction(
                CurrencyEntity(
                    selledCurrency.currencyCode,
                    selledCurrencyBalance.amount - selledCurrency.amount - commission
                ),
                CurrencyEntity(
                    receivedCurrency.currencyCode,
                    receivedCurrencyBalance.amount + receivedCurrency.amount
                )
            )
        )
        exchangeCounterStorage.addExchange()
    }

    override suspend fun getBalance(currencyCode: String): Currency {
        val entity = balanceDao.getCurrency(currencyCode)
        return Currency(entity.code, entity.amount)
    }

    override suspend fun giveStartingBonus() {
        balanceDao.getBalance()
            .first { it.isNotEmpty() && firstRunStorage.isFirstRun() }
            .also { balanceDao.updateBalance(CurrencyEntity("EUR", 1000.0)) }
    }
}
