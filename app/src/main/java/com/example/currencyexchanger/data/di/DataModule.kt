package com.example.currencyexchanger.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(
        apiModule,
        dbModule,
        mapperModule,
        repositoryModule,
        retrofitModule,
        storageModule
    )
}
