package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.repository.BalanceRepository
import com.example.currencyexchanger.domain.usecase.GetBalanceUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


private const val DATA_UPDATE_TIME = 5 * 1000L
internal class GetBalanceUseCaseImpl(private val balanceRepository: BalanceRepository) : GetBalanceUseCase {
    override suspend fun execute(): Flow<List<Currency>> {
        return flow {
            while (true) {
                emit(balanceRepository.getBalance())
                delay(DATA_UPDATE_TIME)
            }
        }
    }
}
