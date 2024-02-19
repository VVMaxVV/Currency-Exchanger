package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.repository.BalanceRepository
import com.example.currencyexchanger.domain.usecase.GiveStartingBonusUseCase
import kotlinx.coroutines.delay

internal class GiveStartingBonusUseCaseImpl(
    private val balanceRepository: BalanceRepository
) : GiveStartingBonusUseCase {
    override suspend fun execute() {
        delay(2000)
        balanceRepository.giveStartingBonus()
    }
}
