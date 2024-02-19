package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.repository.RateRepository
import com.example.currencyexchanger.domain.usecase.GetCurrencyCodesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val DELAY_TIME = 60 * 1000L

internal class GetCurrencyCodesUseCaseImpl(private val rateRepository: RateRepository) :
    GetCurrencyCodesUseCase {
    override fun execute(): Flow<List<String>> = flow {
        while (true) {
            try {
                emit(rateRepository.getCurrencyCodeList())
            } finally {
                delay(DELAY_TIME)
            }
        }
    }
}
