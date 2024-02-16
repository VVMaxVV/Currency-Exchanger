package com.example.currencyexchanger.domain.usecase

import com.example.currencyexchanger.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface GetBalanceUseCase {
    suspend fun execute(): Flow<List<Currency>?>
}
