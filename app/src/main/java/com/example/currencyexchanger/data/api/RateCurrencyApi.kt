package com.example.currencyexchanger.data.api

import com.example.currencyexchanger.data.model.RateCurrencyResponse
import retrofit2.http.GET

internal interface RateCurrencyApi {
    @GET("/tasks/api/currency-exchange-rates")
    suspend fun getCurrencyRate(): RateCurrencyResponse
}
