package com.example.currencyexchanger.presenter.ui.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.presenter.ui.util.DropDownMenu

@Composable
fun TradeCardItem(
    text: String,
    @DrawableRes iconId: Int?,
    currencyCodeList: List<String>,
    iconTintColor: Color?,
    exchangeAmountState: MutableState<String>,
    currencyCodeField: MutableState<String>,
    isErrorState: MutableState<Boolean>,
    onAmountChanged: () -> Unit = {}
) {
    var exchangeAmount by rememberSaveable { exchangeAmountState }
    Card(
        Modifier.padding(dimensionResource(id = R.dimen.trade_card_item_padding)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.trade_card_item_corner))
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.trade_card_item_height)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconId?.let { id ->
                Icon(
                    painter = painterResource(id),
                    contentDescription = stringResource(id = R.string.arrow_upward_description),
                    modifier =
                    Modifier
                        .weight(0.75f)
                        .fillMaxHeight(),
                    tint = iconTintColor ?: LocalContentColor.current
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = exchangeAmount,
                onValueChange = {
                    exchangeAmount = it
                    onAmountChanged()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier =
                Modifier
                    .weight(2f)
                    .padding(
                        horizontal =
                        dimensionResource(
                            id = R.dimen.trade_card_text_field_horizontal_padding
                        )
                    ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                placeholder = {
                    Text(
                        text = "0.00",
                        style = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
            DropDownMenu(
                Modifier.weight(1.5f),
                isError = isErrorState,
                currencyCodeList = currencyCodeList,
                selectedCode = currencyCodeField
            )
        }
    }
}
