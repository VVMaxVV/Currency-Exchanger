package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.repository.ExchangeCommissionRepository
import com.example.currencyexchanger.domain.usecase.GetNumberTransactionsBeforeFreeCommissionsUseCase
import kotlinx.coroutines.flow.Flow

internal class GetNumberTransactionsBeforeFreeCommissionsUseCaseImpl(
    private val exchangeCommissionRepository: ExchangeCommissionRepository
) : GetNumberTransactionsBeforeFreeCommissionsUseCase {
    override suspend fun execute(): Flow<Int> =
        exchangeCommissionRepository.getNumberTransactionsBeforeFreeCommissions()
}
