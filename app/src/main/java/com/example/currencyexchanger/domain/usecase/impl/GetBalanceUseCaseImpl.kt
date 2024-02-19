package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.repository.BalanceRepository
import com.example.currencyexchanger.domain.usecase.GetBalanceUseCase
import kotlinx.coroutines.flow.Flow

internal class GetBalanceUseCaseImpl(
    private val balanceRepository: BalanceRepository
) : GetBalanceUseCase {
    override suspend fun execute(): Flow<List<Currency>?> {
        return balanceRepository.getBalance()
    }
}
