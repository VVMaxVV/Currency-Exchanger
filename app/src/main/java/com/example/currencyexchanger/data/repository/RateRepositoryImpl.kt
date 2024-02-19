package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.RateCurrencyApi
import com.example.currencyexchanger.data.mapper.RateCurrencyResponseMapper
import com.example.currencyexchanger.domain.model.RateCurrency
import com.example.currencyexchanger.domain.repository.RateRepository

internal class RateRepositoryImpl(
    private val rateApi: RateCurrencyApi,
    private val rateCurrencyResponseMapper: RateCurrencyResponseMapper
) : RateRepository {
    override suspend fun getRateCurrencyList(): List<RateCurrency> =
        rateCurrencyResponseMapper.toRateCurrencyList(rateApi.getCurrencyRate())

    override suspend fun getCurrencyCodeList(): List<String> =
        rateCurrencyResponseMapper.toCurrencyCodeList(rateApi.getCurrencyRate())
}
