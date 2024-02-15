package com.example.currencyexchanger.util

import android.app.Application
import com.example.currencyexchanger.data.di.dataModule
import com.example.currencyexchanger.domain.di.domainModule
import com.example.currencyexchanger.presenter.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyExchangerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyExchangerApplication)
            modules(dataModule, domainModule, presenterModule)
        }
    }
}
