package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.presentation.search.model.Item

internal sealed class SearchAction {
    data class GetItemsAction(val category: String) : SearchAction()
    data class SearchItemsAction(val category: String, val search: String) : SearchAction()
    data class DisplayItemAction(val item: Item) : SearchAction()
}