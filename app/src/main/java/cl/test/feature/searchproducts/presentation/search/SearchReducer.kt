package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.presentation.search.SearchResult.DisplayItemResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.GetItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.SearchItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchUiState.DisplayItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.InitialEmptyItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.LoadingUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.SearchEmptyItemsUiState
import javax.inject.Inject

internal class SearchReducer @Inject constructor() {

    infix fun SearchUiState.reduceWith(result: SearchResult): SearchUiState {
        return when (val previousState = this) {
            is DisplayItemsUiState -> previousState reduceWith result
            is InitialEmptyItemsUiState -> previousState reduceWith result
            is ErrorUiState -> previousState reduceWith result
            is LoadingUiState -> previousState reduceWith result
            is SearchEmptyItemsUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: SearchResult): SearchUiState {
        return when (result) {
            is GetItemsResult.InProgress -> LoadingUiState
            is GetItemsResult.Empty -> InitialEmptyItemsUiState
            is GetItemsResult.Error -> ErrorUiState
            is GetItemsResult.Success -> DisplayItemsUiState(result.items)
            is SearchItemsResult.Empty -> SearchEmptyItemsUiState
            is SearchItemsResult.Error -> ErrorUiState
            is SearchItemsResult.Success -> DisplayItemsUiState(result.items)
            else -> throw RuntimeException(result.toString())
        }
    }

    private infix fun DisplayItemsUiState.reduceWith(result: SearchResult): SearchUiState {
        return when (result) {
            is GetItemsResult.InProgress -> LoadingUiState
            is SearchItemsResult.InProgress -> LoadingUiState
            is DisplayItemResult.Success -> this
            else -> throw RuntimeException(result.toString())
        }
    }

    private infix fun InitialEmptyItemsUiState.reduceWith(result: SearchResult): SearchUiState {
        return when (result) {
            is GetItemsResult.InProgress -> LoadingUiState
            is SearchItemsResult.InProgress -> LoadingUiState
            else -> throw RuntimeException(result.toString())
        }
    }

    private infix fun SearchEmptyItemsUiState.reduceWith(result: SearchResult): SearchUiState {
        return when (result) {
            is GetItemsResult.InProgress -> LoadingUiState
            is SearchItemsResult.InProgress -> LoadingUiState
            else -> throw RuntimeException(result.toString())
        }
    }

    private infix fun ErrorUiState.reduceWith(result: SearchResult): SearchUiState {
        return when (result) {
            is GetItemsResult.InProgress -> LoadingUiState
            is SearchItemsResult.InProgress -> LoadingUiState
            else -> throw RuntimeException(result.toString())
        }
    }
}