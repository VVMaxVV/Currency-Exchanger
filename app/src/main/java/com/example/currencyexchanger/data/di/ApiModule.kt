package com.example.currencyexchanger.data.di

import com.example.currencyexchanger.data.api.RateCurrencyApi
import org.koin.dsl.module
import retrofit2.Retrofit

internal val apiModule = module {
    single { provideCurrencyRateApi(get()) }
}

internal fun provideCurrencyRateApi(retrofit: Retrofit) =
    retrofit.create(RateCurrencyApi::class.java)
