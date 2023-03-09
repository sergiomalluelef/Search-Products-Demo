package cl.test.feature.searchproducts.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cl.test.feature.searchproducts.presentation.SearchViewModel
import cl.test.feature.searchproducts.presentation.search.SearchUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.DisplayItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.InitialEmptyItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.LoadingUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.SearchEmptyItemsUiState
import cl.test.feature.searchproducts.presentation.search.mapper.Constants.CATEGORY
import cl.test.feature.searchproducts.ui.search.components.SearchEmptyScreenComponent
import cl.test.feature.searchproducts.ui.search.components.ErrorScreenComponent
import cl.test.feature.searchproducts.ui.search.components.InitialEmptyScreenComponent
import cl.test.feature.searchproducts.ui.search.components.LoadingComponent
import cl.test.feature.searchproducts.ui.search.components.TopAppBarComponent

@Composable
internal fun SearchScreen(
    intentHandler: SearchIntentHandler,
    uiState: State<SearchUiState>,
    viewModel: SearchViewModel,
) {
    var isTopBarVisible by remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = isTopBarVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                TopAppBarComponent(viewModel.searchText) {
                    viewModel.onSearchTextChanged(it)
                    intentHandler.pressingSearchBarUIntent(CATEGORY, it)
                }
            }
        }
    ) {
        SearchContent(
            intentHandler = intentHandler,
            uiState = uiState,
            paddingValues = it,
            isTopBarVisibleEvent = { isVisible ->
                isTopBarVisible = isVisible
            }
        )
    }
}

@Composable
private fun SearchContent(
    intentHandler: SearchIntentHandler,
    uiState: State<SearchUiState>,
    paddingValues: PaddingValues,
    isTopBarVisibleEvent: (Boolean) -> Unit
) {
    when (val currentState = uiState.value) {
        is DisplayItemsUiState -> DisplaySearchComponent(
            itemList = currentState.items,
            intentHandler = intentHandler,
            paddingValues = paddingValues,
            isTopBarVisibleEvent = isTopBarVisibleEvent
        )
        is InitialEmptyItemsUiState -> InitialEmptyScreenComponent {
            intentHandler.retryIntent(CATEGORY)
        }
        is ErrorUiState -> ErrorScreenComponent {
            intentHandler.retryIntent(CATEGORY)
        }
        is LoadingUiState -> LoadingComponent()
        is SearchEmptyItemsUiState -> SearchEmptyScreenComponent()
    }
}