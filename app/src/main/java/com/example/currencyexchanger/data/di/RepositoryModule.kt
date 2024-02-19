package com.example.currencyexchanger.data.di

import com.example.currencyexchanger.data.repository.BalanceRepositoryImpl
import com.example.currencyexchanger.data.repository.ExchangeCommissionRepositoryImpl
import com.example.currencyexchanger.data.repository.RateRepositoryImpl
import com.example.currencyexchanger.domain.repository.BalanceRepository
import com.example.currencyexchanger.domain.repository.ExchangeCommissionRepository
import com.example.currencyexchanger.domain.repository.RateRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<BalanceRepository> { BalanceRepositoryImpl(get(), get(), get(), get()) }
    single<RateRepository> { RateRepositoryImpl(get(), get()) }
    single<ExchangeCommissionRepository> { ExchangeCommissionRepositoryImpl(get()) }
}
