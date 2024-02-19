package com.example.currencyexchanger.presenter.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.domain.model.RateCurrency
import com.example.currencyexchanger.presenter.ui.item.RateCurrencyItem
import com.example.currencyexchanger.presenter.ui.util.shimmerBrush
import com.example.currencyexchanger.presenter.viewmodel.ExchangeViewModel
import com.example.currencyexchanger.util.ResultOf
import org.koin.androidx.compose.koinViewModel
import java.io.IOException

@Composable
fun RateScreen() {
    val exchangeViewModel = koinViewModel<ExchangeViewModel>()

    LaunchedEffect(Unit) {
        exchangeViewModel.fetchCurrencyRate()
    }

    val timeLastSynchronizing by rememberSaveable { exchangeViewModel.timeLastSynchronizing }

    Text(
        text = stringResource(R.string.header_currency_rate),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.header_vertical_padding)),
        textAlign = TextAlign.Center
    )

    Text(
        text = "${stringResource(id = R.string.time_last_synchronization_label)}: $timeLastSynchronizing",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    )

    val currencyRateList by remember { exchangeViewModel.currencyRateList }

    currencyRateList.let { currencyRateListResult ->
        when (currencyRateListResult) {
            is ResultOf.Success -> RateGridLoaded(currencyRateList = currencyRateListResult.data)
            is ResultOf.Error -> HandleCurrencyRateListError(exception = currencyRateListResult.exception)
            is ResultOf.Loading -> CurrencyRateListLoading()
        }
    }
}

@Composable
private fun CurrencyRateListLoading() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.balance_grid_content_padding_vertical))
            .background(Brush.shimmerBrush())
    )
}

@Composable
private fun HandleCurrencyRateListError(exception: Exception) {
    CurrencyRateListLoading()
    val context = LocalContext.current
    when (exception) {
        is IOException -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.bad_internet_connection_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        else -> Log.e("RateScreen", "Exception: $exception")
    }
}

private const val RATE_GRID_COLUMNS = 2

@Composable
private fun RateGridLoaded(currencyRateList: List<RateCurrency>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(RATE_GRID_COLUMNS),
        contentPadding =
        PaddingValues(
            horizontal = dimensionResource(id = R.dimen.balance_grid_content_padding_horizontal),
            vertical = dimensionResource(id = R.dimen.balance_grid_content_padding_vertical)
        ),
        verticalItemSpacing = dimensionResource(id = R.dimen.balance_grid_vertical_item_spacing),
        modifier = Modifier.fillMaxSize()
    ) {
        items(currencyRateList) {
            RateCurrencyItem(rateCurrency = it)
        }
    }
}
