package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.presentation.search.model.Item

internal sealed class SearchResult {
    sealed class GetItemsResult : SearchResult() {
        object InProgress : GetItemsResult()
        data class Success(val items: List<Item>) : GetItemsResult()
        object Empty : GetItemsResult()
        object Error : GetItemsResult()
    }

    sealed class SearchItemsResult : SearchResult() {
        object InProgress : SearchItemsResult()
        data class Success(val items: List<Item>) : SearchItemsResult()
        object Empty : SearchItemsResult()
        object Error : SearchItemsResult()
    }

    sealed class DisplayItemResult : SearchResult() {
        data class Success(val item: Item) : DisplayItemResult()
    }
}