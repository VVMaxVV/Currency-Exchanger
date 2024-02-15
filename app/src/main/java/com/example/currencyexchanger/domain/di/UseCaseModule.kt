package com.example.currencyexchanger.domain.di

import com.example.currencyexchanger.domain.usecase.GetBalanceUseCase
import com.example.currencyexchanger.domain.usecase.GetCurrencyRateUseCase
import com.example.currencyexchanger.domain.usecase.impl.GetBalanceUseCaseImpl
import com.example.currencyexchanger.domain.usecase.impl.GetCurrencyRateUseCaseImpl
import org.koin.dsl.module

val UseCaseModule =
    module {
        single<GetBalanceUseCase> { GetBalanceUseCaseImpl(get()) }
        single<GetCurrencyRateUseCase> { GetCurrencyRateUseCaseImpl(get()) }
    }
