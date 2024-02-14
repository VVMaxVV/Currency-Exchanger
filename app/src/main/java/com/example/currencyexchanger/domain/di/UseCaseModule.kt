package com.example.currencyexchanger.domain.di

import com.example.currencyexchanger.domain.usecase.GetBalanceUseCase
import com.example.currencyexchanger.domain.usecase.impl.GetBalanceUseCaseImpl
import org.koin.dsl.module

val UseCaseModule = module {
    single<GetBalanceUseCase> { GetBalanceUseCaseImpl(get()) }
}
