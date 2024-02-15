package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.model.RateCurrency
import com.example.currencyexchanger.domain.repository.RateRepository
import com.example.currencyexchanger.domain.usecase.GetCurrencyRateUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class GetCurrencyRateUseCaseImpl(private val rateRepository: RateRepository) : GetCurrencyRateUseCase {
    override suspend fun execute(): Flow<List<RateCurrency>> {
        return flow {
            while (true) {
                emit(rateRepository.getRateCurrencyList())
                delay(5000)
            }
        }
    }
}
