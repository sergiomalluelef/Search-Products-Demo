package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.presentation.search.model.Item

internal sealed class SearchUiState {
    object LoadingUiState : SearchUiState()
    data class DisplayItemsUiState(val items: List<Item>) : SearchUiState()
    object InitialEmptyItemsUiState : SearchUiState()
    object SearchEmptyItemsUiState : SearchUiState()
    object ErrorUiState : SearchUiState()
}