package com.example.currencyexchanger.presenter.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchanger.domain.execption.ExchangeAmountIsZeroOrLessException
import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.domain.model.CurrencyExchange
import com.example.currencyexchanger.domain.model.RateCurrency
import com.example.currencyexchanger.domain.usecase.ExchangeUseCase
import com.example.currencyexchanger.domain.usecase.GetCurrencyCodesUseCase
import com.example.currencyexchanger.domain.usecase.GetCurrencyRateUseCase
import com.example.currencyexchanger.domain.usecase.GetExchangeCommissionsUseCase
import com.example.currencyexchanger.domain.usecase.GetNumberTransactionsBeforeFreeCommissionsUseCase
import com.example.currencyexchanger.presenter.exception.IllegalCurrencyCodeException
import com.example.currencyexchanger.util.ResultOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class ExchangeViewModel(
    private val getCurrencyRateUseCase: GetCurrencyRateUseCase,
    private val getCurrencyCodesUseCase: GetCurrencyCodesUseCase,
    private val exchangeUseCase: ExchangeUseCase,
    private val getExchangeCommissionsUseCase: GetExchangeCommissionsUseCase,
    private val getNumberTransactionsBeforeFreeCommissionsUseCase: GetNumberTransactionsBeforeFreeCommissionsUseCase

) : ViewModel() {
    val sellTextField = mutableStateOf("")
    val sellCurrencyCodeField = mutableStateOf("EUR")
    val sellFieldIsError = mutableStateOf(false)

    val receiveTextField = mutableStateOf("")
    val receiveCurrencyCodeField = mutableStateOf("USD")
    val receiveFieldIsError = mutableStateOf(false)

    private val _currencyRateList = mutableStateOf<ResultOf<List<RateCurrency>>>(ResultOf.Loading)
    val currencyRateList: State<ResultOf<List<RateCurrency>>> get() = _currencyRateList

    private val _timeLastSynchronizing = mutableStateOf("")
    val timeLastSynchronizing: State<String> get() = _timeLastSynchronizing

    private var job: Job? = null

    private val currentRateMap = mutableMapOf<String, Double>()

    private val _currencyCodes = mutableStateOf<ResultOf<List<String>>>(ResultOf.Loading)
    val currencyCodes: State<ResultOf<List<String>>> get() = _currencyCodes

    private val _exchangeResult = mutableStateOf<ResultOf<Unit>>(ResultOf.Loading)
    val exchangeResult: State<ResultOf<Unit>> get() = _exchangeResult

    private val _exchangeCommission = mutableStateOf<ResultOf<String>>(ResultOf.Success("0.00"))
    val exchangeCommission: State<ResultOf<String>> get() = _exchangeCommission

    private val _transactionsOfToFreeCommission = mutableStateOf<ResultOf<Int>>(ResultOf.Loading)
    val transactionsOfToFreeCommission: State<ResultOf<Int>> get() = _transactionsOfToFreeCommission

    fun fetchCurrencyRate() {
        job = viewModelScope.launch {
            _currencyRateList.value = ResultOf.Loading
            try {
                getCurrencyRateUseCase.execute().collect {
                    _currencyRateList.value = ResultOf.Success(it)
                    currentRateMap.putAll(it.associate { it.code to it.rate })
                    _timeLastSynchronizing.value = getCurrentTime()
                }
            } catch (e: Exception) {
                _currencyRateList.value = ResultOf.Error(e)
            }
        }
    }

    fun fetchCurrencyCodes() {
        viewModelScope.launch {
            try {
                getCurrencyCodesUseCase.execute().collect {
                    _currencyCodes.value = ResultOf.Success(it)
                }
            } catch (e: Exception) {
                _currencyCodes.value = ResultOf.Error(e)
            }
        }
    }

    fun fetchNumberOfTransactionsBeforeFreeCommission() {
        viewModelScope.launch {
            _transactionsOfToFreeCommission.value = ResultOf.Loading
            try {
                getNumberTransactionsBeforeFreeCommissionsUseCase.execute().collect {
                    if (it == 0) {
                        _exchangeCommission.value = ResultOf.Success("0.00")
                    }
                    _transactionsOfToFreeCommission.value = ResultOf.Success(it)
                }
            } catch (e: Exception) {
                _transactionsOfToFreeCommission.value = ResultOf.Error(e)
            }
        }
    }

    fun sellPriceChanged() {
        try {
            val sellAmoun = sellTextField.value.toDouble()
            currentRateMap[sellCurrencyCodeField.value]?.let { sellRate ->
                currentRateMap[receiveCurrencyCodeField.value]?.let { receiveRate ->
                    receiveTextField.value = String.format(
                        Locale.US,
                        "%.2f",
                        sellAmoun.times(receiveRate).div(sellRate)
                    )
                }
            }
            calculateCommission()
        } catch (e: NumberFormatException) {
            receiveTextField.value = ""
        }
    }

    fun receivePriceChanged() {
        try {
            val receiveAmoun = receiveTextField.value.toDouble()

            currentRateMap[sellCurrencyCodeField.value]?.let { sellRate ->
                currentRateMap[receiveCurrencyCodeField.value]?.let { receiveRate ->
                    sellTextField.value = String.format(
                        Locale.US,
                        "%.2f",
                        receiveAmoun.times(sellRate).div(receiveRate)
                    )
                }
            }
            calculateCommission()
        } catch (e: NumberFormatException) {
            sellTextField.value = ""
        }
    }

    fun submitExchange() {
        viewModelScope.launch {
            try {
                if (sellFieldIsError.value || receiveFieldIsError.value) {
                    throw ExchangeAmountIsZeroOrLessException()
                }
                _exchangeResult.value = ResultOf.Loading
                exchangeUseCase.execute(
                    CurrencyExchange(
                        sellCurrencyCodeField.value,
                        sellTextField.value.toDouble(),
                        currentRateMap[sellCurrencyCodeField.value]
                            ?: throw IllegalCurrencyCodeException()
                    ),
                    CurrencyExchange(
                        receiveCurrencyCodeField.value,
                        receiveTextField.value.toDouble(),
                        currentRateMap[receiveCurrencyCodeField.value]
                            ?: throw IllegalCurrencyCodeException()
                    )
                )
                _exchangeResult.value = ResultOf.Success(Unit)
            } catch (e: Exception) {
                _exchangeResult.value = ResultOf.Error(e)
            }
            calculateCommission()
        }
    }

    private fun calculateCommission() {
        viewModelScope.launch {
            try {
                if (!sellFieldIsError.value) {
                    _exchangeCommission.value = ResultOf.Loading
                    _exchangeCommission.value = ResultOf.Success(
                        String.format(
                            Locale.US,
                            "%.2f",
                            getExchangeCommissionsUseCase.execute(
                                Currency(
                                    sellCurrencyCodeField.value,
                                    sellTextField.value.toDouble()
                                )
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                _exchangeCommission.value = ResultOf.Error(e)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        return dateFormat.format(Date(currentTime))
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}
