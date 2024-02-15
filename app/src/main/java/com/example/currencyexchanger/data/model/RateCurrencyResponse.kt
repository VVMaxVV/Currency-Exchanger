package com.example.currencyexchanger.data.model

internal data class RateCurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
