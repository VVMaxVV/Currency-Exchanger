package com.example.currencyexchanger.domain.usecase

import com.example.currencyexchanger.domain.model.CurrencyExchange

interface ExchangeUseCase {
    suspend fun execute(selledCurrency: CurrencyExchange, receiver: CurrencyExchange)
}
