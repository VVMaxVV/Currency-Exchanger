package com.example.currencyexchanger.data.model

internal data class ExchangeTransaction(
    val selledCurrency: CurrencyEntity,
    val receivedCurrency: CurrencyEntity
)
