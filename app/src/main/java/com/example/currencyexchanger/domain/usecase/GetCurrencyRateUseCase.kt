package com.example.currencyexchanger.domain.usecase

import com.example.currencyexchanger.domain.model.RateCurrency
import kotlinx.coroutines.flow.Flow

interface GetCurrencyRateUseCase {
    suspend fun execute(): Flow<List<RateCurrency>>
}
