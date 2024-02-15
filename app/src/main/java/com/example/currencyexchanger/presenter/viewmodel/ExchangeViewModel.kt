package com.example.currencyexchanger.presenter.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchanger.domain.model.RateCurrency
import com.example.currencyexchanger.domain.usecase.GetCurrencyRateUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

internal class ExchangeViewModel(
    private val getCurrencyRateUseCase: GetCurrencyRateUseCase
) : ViewModel() {
    val sellTextField = mutableStateOf("")
    val sellFieldIsError = mutableStateOf(false)

    val receiveTextField = mutableStateOf("")
    val receiveFieldIsError = mutableStateOf(false)

    private val _currencyRateList = mutableStateOf<List<RateCurrency>?>(null)
    val currencyRateList: State<List<RateCurrency>?> get() = _currencyRateList

    private val _timeLastSynchronizing = mutableStateOf("")
    val timeLastSynchronizing: State<String> get() = _timeLastSynchronizing

    fun fetchCurrencyRate() {
        viewModelScope.launch {
            getCurrencyRateUseCase.execute().collect {
                _currencyRateList.value = it
                _timeLastSynchronizing.value = getCurrentTime()
            }
        }
    }

    private fun getCurrentTime(): String {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        return dateFormat.format(Date(currentTime))
    }
}
