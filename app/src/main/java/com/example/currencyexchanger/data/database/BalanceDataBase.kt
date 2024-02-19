package com.example.currencyexchanger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyexchanger.data.dao.BalanceDAO
import com.example.currencyexchanger.data.model.CurrencyEntity

private const val DATABASE_VERSION = 1

@Database(entities = [(CurrencyEntity::class)], version = DATABASE_VERSION)
internal abstract class BalanceDataBase : RoomDatabase() {
    abstract fun getBalanceDao(): BalanceDAO
}
