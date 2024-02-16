package com.example.currencyexchanger.presenter.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.usecase.GetBalanceUseCase
import com.example.currencyexchanger.domain.usecase.GiveStartingBonusUseCase
import kotlinx.coroutines.launch

internal class BalanceViewModel(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val giveStartingBonusUseCase: GiveStartingBonusUseCase
) : ViewModel() {
    val currencyList = mutableStateOf<List<Currency>>(emptyList())

    fun fetchBalance() {
        viewModelScope.launch {
            getBalanceUseCase.execute().collect {
                it?.let { currencyList.value = it }
            }
        }
    }

    fun giveStartingBonus() {
        viewModelScope.launch {
            giveStartingBonusUseCase.execute()
        }
    }
}
