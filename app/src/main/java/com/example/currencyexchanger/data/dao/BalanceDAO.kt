package com.example.currencyexchanger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.currencyexchanger.data.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BalanceDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCurrency(currencyEntity: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCurrency(currencyEntityList: List<CurrencyEntity>)

    @Query("SELECT * FROM balance")
    fun getBalance(): Flow<List<CurrencyEntity>>

    @Update
    suspend fun updateBalance(currencyEntityList: List<CurrencyEntity>)
}
