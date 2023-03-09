package cl.test.feature.searchproducts.ui.search


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cl.test.feature.searchproducts.R
import cl.test.feature.searchproducts.presentation.search.model.Item
import cl.test.feature.searchproducts.ui.search.components.MarketCardComponent
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
internal fun DisplaySearchComponent(
    itemList: List<Item>,
    intentHandler: SearchIntentHandler,
    paddingValues: PaddingValues,
    isTopBarVisibleEvent: (Boolean) -> Unit
) {
    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState) {
        snapshotFlow {
            gridState.firstVisibleItemIndex <= 0
        }.distinctUntilChanged().collect {
            isTopBarVisibleEvent.invoke(it)
        }
    }
    Column(
        modifier = Modifier.padding(
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {
        Text(
            text = stringResource(id = R.string.vehicle_accessories),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
        ) {
            items(itemList) { item ->
                MarketCardComponent(item) {
                    intentHandler.pressingItemUIntent(item)
                }
            }
        }
    }
}