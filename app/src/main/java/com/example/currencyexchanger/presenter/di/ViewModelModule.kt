package com.example.currencyexchanger.presenter.di

import com.example.currencyexchanger.presenter.viewmodel.BalanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { BalanceViewModel(get()) }
}
