package com.example.currencyexchanger.data.di

import com.example.currencyexchanger.data.repository.BalanceRepositoryImpl
import com.example.currencyexchanger.domain.repository.BalanceRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<BalanceRepository> { BalanceRepositoryImpl() }
}
