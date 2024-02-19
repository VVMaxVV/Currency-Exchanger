package com.example.currencyexchanger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.currencyexchanger.data.model.CurrencyEntity
import com.example.currencyexchanger.data.model.ExchangeTransaction
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BalanceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(currencyEntity: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(currencyEntityList: List<CurrencyEntity>)

    @Query("SELECT * FROM balance")
    fun getBalance(): Flow<List<CurrencyEntity>>

    @Update
    suspend fun updateBalance(currencyEntityList: CurrencyEntity)

    @Transaction
    suspend fun applyTransaction(exchangeTransaction: ExchangeTransaction) {
        updateBalance(exchangeTransaction.selledCurrency)
        updateBalance(exchangeTransaction.receivedCurrency)
    }

    @Query("SELECT * FROM balance WHERE code = :code")
    suspend fun getCurrency(code: String): CurrencyEntity
}
