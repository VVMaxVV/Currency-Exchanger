package com.example.currencyexchanger.presenter.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.R.drawable
import com.example.currencyexchanger.presenter.ui.item.TradeCardItem
import com.example.currencyexchanger.presenter.viewmodel.ExchangeViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TradeScreen() {
    val exchangeViewModel = koinViewModel<ExchangeViewModel>().apply {
        fetchCurrencyRate()
    }

    Text(
        text = stringResource(id = R.string.header_currency_exchange),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.header_vertical_padding)),
        textAlign = TextAlign.Center
    )

    TradeCardItem(
        text = stringResource(id = R.string.label_sell),
        iconId = drawable.arrow_upward,
        iconTintColor = Color.Red,
        exchangeAmountState = exchangeViewModel.sellTextField,
        isErrorState = exchangeViewModel.sellFieldIsError
    )

    TradeCardItem(
        text = stringResource(id = R.string.label_receive),
        iconId = drawable.arrow_downward,
        iconTintColor = Color.Green,
        exchangeAmountState = exchangeViewModel.receiveTextField,
        isErrorState = exchangeViewModel.receiveFieldIsError
    )

    SubmitButton(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.button_padding))
    )
}

@Composable
private fun SubmitButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.button_text_submit))
    }
}
