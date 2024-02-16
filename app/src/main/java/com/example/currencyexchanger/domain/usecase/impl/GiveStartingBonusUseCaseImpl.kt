package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.repository.BalanceRepository
import com.example.currencyexchanger.domain.repository.RateRepository
import com.example.currencyexchanger.domain.usecase.GiveStartingBonusUseCase

internal class GiveStartingBonusUseCaseImpl(
    private val balanceRepository: BalanceRepository,
    private val rateRepository: RateRepository
) : GiveStartingBonusUseCase {
    override suspend fun execute() {
        balanceRepository.updateBalance(
            listOf(Currency("EUR", 1000.0))
        )
    }
}
