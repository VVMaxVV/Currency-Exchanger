package com.example.currencyexchanger.presenter.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.domain.model.Currency
import com.example.currencyexchanger.presenter.ui.item.BalanceCurrencyItem
import com.example.currencyexchanger.presenter.ui.util.ShimmerBox
import com.example.currencyexchanger.presenter.viewmodel.BalanceViewModel
import com.example.currencyexchanger.util.ResultOf
import org.koin.androidx.compose.koinViewModel

private const val GRID_COLUMNS_NUMBER = 2
private const val GRID_ROWS_NUMBER = 3

@Composable
fun BalanceScreen(modifier: Modifier = Modifier) {
    val balanceViewModel: BalanceViewModel = koinViewModel<BalanceViewModel>()

    LaunchedEffect(Unit) {
        balanceViewModel.fetchBalance()
        balanceViewModel.giveStartingBonus()
    }

    HandleStartBonusStatus()

    Column(modifier) {
        Text(
            text = stringResource(R.string.header_my_balances),
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.header_vertical_padding)),
            textAlign = TextAlign.Center
        )

        HandleCurrencyListResult()
    }
}

@Composable
private fun HandleStartBonusStatus(balanceViewModel: BalanceViewModel = koinViewModel()) {
    val result by remember { balanceViewModel.startBonusStatus }
    val context = LocalContext.current
    when (result) {
        is ResultOf.Success -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.starting_bonus_toast),
                Toast.LENGTH_SHORT
            ).show()
        }

        is ResultOf.Error -> {
            Log.e("HandleStartBonusStatus", "Exception: ${(result as ResultOf.Error).exception}")
        }

        else -> {}
    }
}

@Composable
private fun HandleCurrencyListResult(balanceViewModel: BalanceViewModel = koinViewModel()) {
    val result by remember { balanceViewModel.currencyList }
    when (result) {
        is ResultOf.Success -> {
            BalanceGrid(currencyList = (result as ResultOf.Success<List<Currency>>).data)
        }

        is ResultOf.Loading -> {
            ShimmerBox(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
            )
        }

        is ResultOf.Error -> {
            Text(
                text = stringResource(id = R.string.failed_to_get_current_balance_label),
                Modifier.padding(
                    dimensionResource(id = R.dimen.header_vertical_padding)
                )
            )
            Button(
                onClick = {
                    balanceViewModel.fetchBalance()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.button_padding))
            ) {
                Text(text = stringResource(id = R.string.button_text_try_refresh))
            }
        }
    }
}

@Composable
private fun BalanceGrid(currencyList: List<Currency>) {
    var isGridExpanded by rememberSaveable { mutableStateOf(false) }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(GRID_COLUMNS_NUMBER),
        contentPadding =
        PaddingValues(
            horizontal = dimensionResource(id = R.dimen.balance_grid_content_padding_horizontal),
            vertical = dimensionResource(id = R.dimen.balance_grid_content_padding_vertical)
        ),
        verticalItemSpacing = dimensionResource(id = R.dimen.balance_grid_vertical_item_spacing),
        modifier = Modifier.heightIn(max = dimensionResource(id = R.dimen.balance_grid_max_height))
    ) {
        items(
            if (isGridExpanded) {
                currencyList
            } else {
                currencyList.take(GRID_COLUMNS_NUMBER * GRID_ROWS_NUMBER)
            }
        ) {
            BalanceCurrencyItem(
                name = it.name,
                amount = it.amount
            )
        }
    }
    Button(
        onClick = {
            isGridExpanded = !isGridExpanded
        },
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.button_padding))
    ) {
        Text(
            text =
            stringResource(
                if (isGridExpanded) {
                    R.string.button_text_show_less
                } else {
                    R.string.button_text_show_more
                }
            )
        )
    }
}
