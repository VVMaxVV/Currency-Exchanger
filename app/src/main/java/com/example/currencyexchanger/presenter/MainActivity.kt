package com.example.currencyexchanger.presenter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyexchanger.presenter.ui.screen.BalanceScreen
import com.example.currencyexchanger.presenter.ui.screen.RateScreen
import com.example.currencyexchanger.presenter.ui.screen.TradeScreen
import com.example.currencyexchanger.presenter.ui.theme.CurrencyExchangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyExchangerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Greeting() {
    Column(Modifier.fillMaxSize()) {
        BalanceScreen()
        Divider()
        TradeScreen()
        Divider()
        RateScreen()
    }
}

@Preview
@Composable
fun GreetingPreview() {
    CurrencyExchangerTheme {
        Greeting()
    }
}
