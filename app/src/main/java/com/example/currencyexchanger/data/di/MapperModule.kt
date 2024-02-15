package com.example.currencyexchanger.data.di

import com.example.currencyexchanger.data.mapper.RateCurrencyResponseMapper
import org.koin.dsl.module

internal val mapperModule = module {
    single { RateCurrencyResponseMapper() }
}
