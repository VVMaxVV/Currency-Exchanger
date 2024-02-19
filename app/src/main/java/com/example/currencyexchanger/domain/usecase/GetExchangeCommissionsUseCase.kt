package com.example.currencyexchanger.domain.usecase

import com.example.currencyexchanger.domain.model.Currency

interface GetExchangeCommissionsUseCase {
    suspend fun execute(currency: Currency): Double
}
