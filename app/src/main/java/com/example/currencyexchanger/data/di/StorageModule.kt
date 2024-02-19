package com.example.currencyexchanger.data.di

import com.example.currencyexchanger.data.storege.ExchangeCounterStorage
import com.example.currencyexchanger.data.storege.FirstRunStorage
import com.example.currencyexchanger.data.storege.impl.ExchangeCounterStorageImpl
import com.example.currencyexchanger.data.storege.impl.FirstRunStorageImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val storageModule = module {
    single<ExchangeCounterStorage> { ExchangeCounterStorageImpl(androidContext()) }
    single<FirstRunStorage> { FirstRunStorageImpl(androidContext()) }
}
