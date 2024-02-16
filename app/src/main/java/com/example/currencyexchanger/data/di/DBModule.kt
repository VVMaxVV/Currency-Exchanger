package com.example.currencyexchanger.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.currencyexchanger.data.database.BalanceDataBase
import com.example.currencyexchanger.data.model.CurrencyEntity
import com.example.currencyexchanger.domain.repository.RateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val BASE_AMOUND_CURRENCY = 0.0

internal val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            BalanceDataBase::class.java,
            "ApplicationDB"
        ).allowMainThreadQueries().addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    get<BalanceDataBase>().getBalanceDao().addCurrency(
                        get<RateRepository>().getCurrencyCodeList().map {
                            CurrencyEntity(it, BASE_AMOUND_CURRENCY)
                        }
                    )
                }
            }
        }).build()
    }
    single { get<BalanceDataBase>().getBalanceDao() }
}
