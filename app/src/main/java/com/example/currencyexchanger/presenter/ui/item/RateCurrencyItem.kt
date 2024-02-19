package com.example.currencyexchanger.presenter.ui.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.currencyexchanger.R
import com.example.currencyexchanger.domain.model.RateCurrency
import java.util.Locale

@Composable
fun RateCurrencyItem(rateCurrency: RateCurrency, modifier: Modifier = Modifier) {
    Card(modifier) {
        Text(
            text = "${rateCurrency.code} " +
                String.format(Locale.US, "%.4f", rateCurrency.rate),
            modifier = Modifier.padding(
                horizontal = dimensionResource(
                    id = R.dimen.balance_card_item_label_horizontal_padding
                )
            ),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}
