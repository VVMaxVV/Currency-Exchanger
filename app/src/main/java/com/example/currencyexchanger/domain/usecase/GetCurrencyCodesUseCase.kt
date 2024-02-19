package com.example.currencyexchanger.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetCurrencyCodesUseCase {
    fun execute(): Flow<List<String>>
}
