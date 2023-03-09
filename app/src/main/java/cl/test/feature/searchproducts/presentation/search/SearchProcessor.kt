package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.data.SearchProductsDataRepository
import cl.test.feature.searchproducts.presentation.search.SearchAction.DisplayItemAction
import cl.test.feature.searchproducts.presentation.search.SearchAction.GetItemsAction
import cl.test.feature.searchproducts.presentation.search.SearchAction.SearchItemsAction
import cl.test.feature.searchproducts.presentation.search.SearchResult.DisplayItemResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.GetItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.SearchItemsResult
import cl.test.feature.searchproducts.presentation.search.mapper.SearchMapper
import cl.test.feature.searchproducts.presentation.search.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class SearchProcessor @Inject constructor(
    private val repository: SearchProductsDataRepository,
    private val mapper: SearchMapper
) {
    fun actionProcessor(actions: SearchAction): Flow<SearchResult> =
        when (actions) {
            is GetItemsAction -> getItemsProcessor(actions.category)
            is SearchItemsAction -> getSearchItemsProcessor(actions.category, actions.search)
            is DisplayItemAction -> displayItemProcessor(actions.item)
        }

    private fun getItemsProcessor(category: String): Flow<GetItemsResult> =
        repository.getItemsByCategory(category).map {
            val itemsResult = with(mapper) { it.toItems() }.itemsResult
            if (itemsResult.isEmpty()) {
                GetItemsResult.Empty
            } else {
                GetItemsResult.Success(
                    itemsResult
                )
            }
        }.onStart {
            emit(GetItemsResult.InProgress)
        }.catch {
            emit(GetItemsResult.Error)
        }.flowOn(Dispatchers.IO)

    private fun getSearchItemsProcessor(category: String, search: String) =
        flow {
            val response = if (search.isBlank() || search.isEmpty()) {
                repository.getItemsByCategory(category).first()
            } else {
                repository.getSearchInCategory(category, search).first()
            }
            val itemsResult = with(mapper) { response.toItems() }.itemsResult

            if (itemsResult.isEmpty()) {
                emit(SearchItemsResult.Empty)
            } else {
                emit(SearchItemsResult.Success(itemsResult))
            }
        }.onStart {
            emit(SearchItemsResult.InProgress)
        }.catch {
            emit(SearchItemsResult.Error)
        }.flowOn(Dispatchers.IO)

    private fun displayItemProcessor(item: Item) =
        flow<DisplayItemResult> {
            emit(DisplayItemResult.Success(item))
        }
}