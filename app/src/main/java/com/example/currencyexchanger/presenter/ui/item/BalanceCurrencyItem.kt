package com.example.currencyexchanger.presenter.ui.item

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BalanceCurrencyItem(name: String, amount: String, modifier: Modifier = Modifier) {
    Card {
        Text(text = "$amount $name", modifier)
    }
}