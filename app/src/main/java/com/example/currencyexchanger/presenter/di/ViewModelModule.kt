package com.example.currencyexchanger.presenter.di

import com.example.currencyexchanger.presenter.viewmodel.BalanceViewModel
import com.example.currencyexchanger.presenter.viewmodel.ExchangeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BalanceViewModel(get(), get()) }
    viewModel { ExchangeViewModel(get(), get(), get(), get(), get()) }
}
