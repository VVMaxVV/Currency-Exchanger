package com.example.currencyexchanger.presenter.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.R.drawable
import com.example.currencyexchanger.presenter.ui.item.TradeCardItem

@SuppressLint("UnrememberedMutableState")
@Composable
fun TradeScreen() {
    Text(
        text = stringResource(id = R.string.header_currency_exchange),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.header_vertical_padding)),
        textAlign = TextAlign.Center
    )

    val sellTextField = mutableStateOf("")
    val sellFieldIsError = mutableStateOf(false)

    TradeCardItem(
        text = stringResource(id = R.string.label_sell),
        iconId = drawable.arrow_upward,
        iconTintColor = Color.Red,
        exchangeAmountState = sellTextField,
        isErrorState = sellFieldIsError
    )

    val receiveTextField = mutableStateOf("")
    val receiveFieldIsError = mutableStateOf(false)

    TradeCardItem(
        text = stringResource(id = R.string.label_receive),
        iconId = drawable.arrow_downward,
        iconTintColor = Color.Green,
        exchangeAmountState = receiveTextField,
        isErrorState = receiveFieldIsError
    )
}
