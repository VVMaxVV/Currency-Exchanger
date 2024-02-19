package com.example.currencyexchanger.presenter.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.usecase.GetBalanceUseCase
import com.example.currencyexchanger.domain.usecase.GiveStartingBonusUseCase
import com.example.currencyexchanger.util.ResultOf
import kotlinx.coroutines.launch

internal class BalanceViewModel(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val giveStartingBonusUseCase: GiveStartingBonusUseCase
) : ViewModel() {
    private val _currencyList = mutableStateOf<ResultOf<List<Currency>>>(ResultOf.Loading)
    val currencyList: State<ResultOf<List<Currency>>> get() = _currencyList

    private val _startBonusStatus = mutableStateOf<ResultOf<Unit>>(ResultOf.Loading)
    val startBonusStatus: State<ResultOf<Unit>> = _startBonusStatus

    fun fetchBalance() {
        viewModelScope.launch {
            try {
                _currencyList.value = ResultOf.Loading
                getBalanceUseCase.execute().collect {
                    it?.let {
                        _currencyList.value = ResultOf.Success(it)
                    }
                }
            } catch (e: Exception) {
                _currencyList.value = ResultOf.Error(e)
            }
        }
    }

    fun giveStartingBonus() {
        viewModelScope.launch {
            try {
                giveStartingBonusUseCase.execute()
                _startBonusStatus.value = ResultOf.Success(Unit)
            } catch (e: Exception) {
                _startBonusStatus.value = ResultOf.Error(e)
            }
        }
    }
}
