package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.repository.BalanceRepository

internal class BalanceRepositoryImpl: BalanceRepository {
    private var balance = listOf(
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
        Currency("EUR", "1000.00"),
        Currency("USD", "0.00"),
        Currency("UAH", "0.00"),
    )
    override suspend fun getBalance(): List<Currency> = balance
}
