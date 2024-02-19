package com.example.currencyexchanger.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetNumberTransactionsBeforeFreeCommissionsUseCase {
    suspend fun execute(): Flow<Int>
}
