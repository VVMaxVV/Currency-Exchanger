package com.example.currencyexchanger.data.mapper

import com.example.currencyexchanger.data.model.RateCurrencyResponse
import com.example.currencyexchanger.domain.model.RateCurrency

internal class RateCurrencyResponseMapper {
    fun toRateCurrencyList(rateCurrencyResponse: RateCurrencyResponse) =
        rateCurrencyResponse.rates.map { RateCurrency(it.key, it.value) }

    fun toCurrencyCodeList(rateCurrencyResponse: RateCurrencyResponse) =
        rateCurrencyResponse.rates.map { it.key }
}
