package com.example.currencyexchanger.domain.usecase.impl

import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.model.CurrencyExchange
import com.example.currencyexchanger.domain.repository.ExchangeCommissionRepository
import com.example.currencyexchanger.domain.repository.RateRepository
import com.example.currencyexchanger.domain.usecase.GetExchangeCommissionsUseCase
import com.example.currencyexchanger.presenter.exception.IllegalCurrencyCodeException

internal class GetExchangeCommissionsUseCaseImpl(
    private val exchangeCommissionRepository: ExchangeCommissionRepository,
    private val rateRepository: RateRepository
) : GetExchangeCommissionsUseCase {
    override suspend fun execute(currency: Currency): Double {
        val rate = rateRepository.getRateCurrencyList().find {
            it.code == currency.name
        }
        return exchangeCommissionRepository.getExchangeCommissions(
            CurrencyExchange(
                currency.name,
                currency.amount,
                rate?.rate ?: throw IllegalCurrencyCodeException()
            )
        )
    }
}
