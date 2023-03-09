package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.presentation.search.model.Item

internal sealed class SearchUIntent {
    data class InitialUIntent(val category: String): SearchUIntent()
    data class PressingSearchBarUIntent(val category: String, val search: String): SearchUIntent()
    data class PressingItemUIntent(val item: Item): SearchUIntent()
}