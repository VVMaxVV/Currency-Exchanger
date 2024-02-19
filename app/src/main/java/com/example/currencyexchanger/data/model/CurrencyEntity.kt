package com.example.currencyexchanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balance")
internal data class CurrencyEntity(@PrimaryKey val code: String, val amount: Double)
