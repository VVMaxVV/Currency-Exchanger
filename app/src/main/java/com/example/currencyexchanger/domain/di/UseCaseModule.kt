package com.example.currencyexchanger.domain.di

import com.example.currencyexchanger.domain.usecase.ExchangeUseCase
import com.example.currencyexchanger.domain.usecase.GetBalanceUseCase
import com.example.currencyexchanger.domain.usecase.GetCurrencyCodesUseCase
import com.example.currencyexchanger.domain.usecase.GetCurrencyRateUseCase
import com.example.currencyexchanger.domain.usecase.GetExchangeCommissionsUseCase
import com.example.currencyexchanger.domain.usecase.GetNumberTransactionsBeforeFreeCommissionsUseCase
import com.example.currencyexchanger.domain.usecase.GiveStartingBonusUseCase
import com.example.currencyexchanger.domain.usecase.impl.ExchangeUseCaseImpl
import com.example.currencyexchanger.domain.usecase.impl.GetBalanceUseCaseImpl
import com.example.currencyexchanger.domain.usecase.impl.GetCurrencyCodesUseCaseImpl
import com.example.currencyexchanger.domain.usecase.impl.GetCurrencyRateUseCaseImpl
import com.example.currencyexchanger.domain.usecase.impl.GetExchangeCommissionsUseCaseImpl
import com.example.currencyexchanger.domain.usecase.impl.GetNumberTransactionsBeforeFreeCommissionsUseCaseImpl
import com.example.currencyexchanger.domain.usecase.impl.GiveStartingBonusUseCaseImpl
import org.koin.dsl.module

val UseCaseModule =
    module {
        single<GetBalanceUseCase> { GetBalanceUseCaseImpl(get()) }
        single<GetCurrencyRateUseCase> { GetCurrencyRateUseCaseImpl(get()) }
        single<GiveStartingBonusUseCase> { GiveStartingBonusUseCaseImpl(get()) }
        single<GetCurrencyCodesUseCase> { GetCurrencyCodesUseCaseImpl(get()) }
        single<ExchangeUseCase> { ExchangeUseCaseImpl(get()) }
        single<GetExchangeCommissionsUseCase> { GetExchangeCommissionsUseCaseImpl(get(), get()) }
        single<GetNumberTransactionsBeforeFreeCommissionsUseCase> {
            GetNumberTransactionsBeforeFreeCommissionsUseCaseImpl(get())
        }
    }
