package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.dao.BalanceDAO
import com.example.currencyexchanger.data.model.CurrencyEntity
import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.repository.BalanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BalanceRepositoryImpl(
    private val balanceDao: BalanceDAO
) : BalanceRepository {
    override suspend fun getBalance(): Flow<List<Currency>?> =
        balanceDao.getBalance().map {
            it.map { Currency(it.code, it.amount) }
        }

    override suspend fun updateBalance(updatedCurrencyList: List<Currency>) {
        balanceDao.updateBalance(
            updatedCurrencyList.map {
                CurrencyEntity(it.name, it.amount)
            }
        )
    }
}
