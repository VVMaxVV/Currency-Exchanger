package com.example.currencyexchanger.presenter.ui.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.currencyexchanger.R

@Composable
fun BalanceCurrencyItem(
    name: String,
    amount: String,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Text(
            text = "$amount $name",
            modifier = Modifier.padding(
                horizontal = dimensionResource(
                    id = R.dimen.balance_card_item_label_horizontal_padding
                )
            )
        )
    }
}
