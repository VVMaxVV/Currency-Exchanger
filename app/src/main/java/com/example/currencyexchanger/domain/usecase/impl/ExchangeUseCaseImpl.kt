package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.model.CurrencyExchange
import com.example.currencyexchanger.domain.repository.BalanceRepository
import com.example.currencyexchanger.domain.usecase.ExchangeUseCase

internal class ExchangeUseCaseImpl(
    private val balanceRepository: BalanceRepository
) : ExchangeUseCase {
    override suspend fun execute(
        selledCurrency: CurrencyExchange,
        receivedCurrency: CurrencyExchange
    ) {
        balanceRepository.updateBalance(
            selledCurrency,
            receivedCurrency
        )
    }
}
