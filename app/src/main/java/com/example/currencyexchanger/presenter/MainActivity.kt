package com.example.currencyexchanger.presenter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyexchanger.R
import com.example.currencyexchanger.presenter.ui.screen.BalanceScreen
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
        Column {
            BalanceScreen()
            Divider()
            TradeScreen()
        }

        SubmitButton(
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.button_padding))
        )
    }
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

@Preview
@Composable
fun GreetingPreview() {
    CurrencyExchangerTheme {
        Greeting()
    }
}
