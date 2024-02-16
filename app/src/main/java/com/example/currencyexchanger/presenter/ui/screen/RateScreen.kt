package com.example.currencyexchanger.presenter.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.presenter.ui.item.RateCurrencyItem
import com.example.currencyexchanger.presenter.viewmodel.ExchangeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RateScreen() {
    val exchangeViewModel = koinViewModel<ExchangeViewModel>().apply {
        fetchCurrencyRate()
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
        text = "Time last synchronization: $timeLastSynchronizing",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    )

    val currencyRateList by remember { exchangeViewModel.currencyRateList }

    currencyRateList?.let { currencyRateList ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
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
}
