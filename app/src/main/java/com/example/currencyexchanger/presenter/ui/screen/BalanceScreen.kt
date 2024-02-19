package com.example.currencyexchanger.presenter.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchanger.R
import com.example.currencyexchanger.presenter.ui.item.BalanceCurrencyItem
import com.example.currencyexchanger.presenter.viewmodel.BalanceViewModel
import org.koin.androidx.compose.koinViewModel

private const val GRID_COLUMNS_NUMBER = 2
private const val GRID_ROWS_NUMBER = 3

@Composable
fun BalanceScreen(modifier: Modifier = Modifier) {
    val balanceViewModel: BalanceViewModel = koinViewModel<BalanceViewModel>()
    val currencyList by balanceViewModel.currencyList
    var isGridExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        balanceViewModel.fetchBalance()
        balanceViewModel.giveStartingBonus()
    }

    Column(modifier) {
        Text(
            text = stringResource(R.string.header_my_balances),
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.header_vertical_padding)),
            textAlign = TextAlign.Center
        )

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
}
