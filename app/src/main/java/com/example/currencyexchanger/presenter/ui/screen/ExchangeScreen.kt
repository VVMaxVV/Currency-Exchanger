package com.example.currencyexchanger.presenter.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.R.drawable
import com.example.currencyexchanger.domain.execption.ExchangeAmountIsZeroOrLessException
import com.example.currencyexchanger.domain.execption.IllegalBalanceState
import com.example.currencyexchanger.presenter.exception.IllegalCurrencyCodeException
import com.example.currencyexchanger.presenter.ui.item.TradeCardItem
import com.example.currencyexchanger.presenter.viewmodel.ExchangeViewModel
import com.example.currencyexchanger.util.ResultOf
import org.koin.androidx.compose.koinViewModel
import java.io.IOException

@SuppressLint("UnrememberedMutableState")
@Composable
fun TradeScreen() {
    val exchangeViewModel = koinViewModel<ExchangeViewModel>()

    LaunchedEffect(Unit) {
        exchangeViewModel.fetchCurrencyCodes()
        exchangeViewModel.fetchNumberOfTransactionsBeforeFreeCommission()
    }

    val exchangeResult by remember { exchangeViewModel.exchangeResult }

    HandleExchangeResult(exchangeResult = exchangeResult)

    Text(
        text = stringResource(id = R.string.header_currency_exchange),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.header_vertical_padding)),
        textAlign = TextAlign.Center
    )

    val currencyCodes by remember { exchangeViewModel.currencyCodes }

    currencyCodes.let { result ->
        when (result) {
            is ResultOf.Success -> TradeCards(result.data)
            else -> TradeCards()
        }
    }

    val exchangeCommission by remember { exchangeViewModel.exchangeCommission }
    val codeCurrencyForCommission by remember { exchangeViewModel.sellCurrencyCodeField }

    HandleCommissionResult(exchangeCommission, codeCurrencyForCommission)

    val freeCommissionsCounter by remember { exchangeViewModel.transactionsOfToFreeCommission }

    HandleFreeCommissionCounter(freeCommissionsCounter)

    SubmitButton(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.button_padding))
    ) {
        exchangeViewModel.submitExchange()
    }
}

@Composable
private fun HandleExchangeResult(exchangeResult: ResultOf<Unit>) {
    val context = LocalContext.current
    exchangeResult.let { result ->
        when (result) {
            is ResultOf.Success -> {
                Toast.makeText(
                    context,
                    stringResource(id = R.string.successfully_transaction_toast),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is ResultOf.Error -> {
                HandleExchangeResultError(result.exception)
            }

            is ResultOf.Loading -> { /*TODO()*/
            }
        }
    }
}

@Composable
private fun HandleExchangeResultError(e: Exception) {
    val context = LocalContext.current
    when (e) {
        is IllegalCurrencyCodeException -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.exchange_code_problem_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        is ExchangeAmountIsZeroOrLessException -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.exchange_amount_is_zero_or_less_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        is IllegalBalanceState -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.not_enough_currency_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        is IOException -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.bad_internet_connection_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        else -> {
            Log.e("HandleExchangeResult", "Exception: $e")
        }
    }
}

@Composable
private fun TradeCards(
    currencyCodes: List<String> = emptyList()
) {
    val exchangeViewModel = koinViewModel<ExchangeViewModel>()
    TradeCardItem(
        text = stringResource(id = R.string.label_sell),
        iconId = drawable.arrow_upward,
        iconTintColor = Color.Red,
        exchangeAmountState = exchangeViewModel.sellTextField,
        currencyCodeField = exchangeViewModel.sellCurrencyCodeField,
        currencyCodeList = currencyCodes,
        isErrorState = exchangeViewModel.sellFieldIsError,
        onAmountChanged = {
            exchangeViewModel.sellPriceChanged()
        }
    )

    TradeCardItem(
        text = stringResource(id = R.string.label_receive),
        iconId = drawable.arrow_downward,
        iconTintColor = Color.Green,
        exchangeAmountState = exchangeViewModel.receiveTextField,
        currencyCodeField = exchangeViewModel.receiveCurrencyCodeField,
        currencyCodeList = currencyCodes,
        isErrorState = exchangeViewModel.receiveFieldIsError,
        onAmountChanged = {
            exchangeViewModel.receivePriceChanged()
        }
    )
}

@Composable
private fun HandleCommissionResult(result: ResultOf<String>, selectedCurrency: String) {
    val context = LocalContext.current
    when (result) {
        is ResultOf.Success -> {
            CurrencyExchangeScreenText("${stringResource(id = R.string.commission_label)}: ${result.data} $selectedCurrency")
        }

        is ResultOf.Loading -> {
            CurrencyExchangeScreenText("${stringResource(id = R.string.commission_label)}: ${stringResource(id = R.string.loading_label)}")
        }

        is ResultOf.Error -> {
            CurrencyExchangeScreenText("${stringResource(id = R.string.commission_label)}: ${stringResource(id = R.string.error_label)}")
            when (result.exception) {
                is IOException -> {
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.bad_internet_connection_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Log.e("HandleCommissionResult", "Exception: ${result.exception}")
            }
        }
    }
}

@Composable
private fun CurrencyExchangeScreenText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.commission_label_vertical_padding),
                horizontal = dimensionResource(id = R.dimen.commission_label_horizontal_padding)
            )
    )
}

@Composable
private fun HandleFreeCommissionCounter(counter: ResultOf<Int>) {
    when (counter) {
        is ResultOf.Success -> {
            CurrencyExchangeScreenText(text = "${stringResource(R.string.transactions_up_to_free_commission_toast)}: ${counter.data}")
        }

        is ResultOf.Loading -> {
            CurrencyExchangeScreenText(
                text = "${stringResource(R.string.transactions_up_to_free_commission_toast)}: " +
                    "${stringResource(id = R.string.error_label)}"
            )
        }

        is ResultOf.Error -> {
            Log.e("HandleFreeCommissionCounter", "Exception: ${counter.exception}")
        }
    }
}

@Composable
private fun SubmitButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.button_text_submit))
    }
}
