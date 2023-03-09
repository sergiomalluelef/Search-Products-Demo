package cl.test.feature.searchproducts.presentation

import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItem
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItems
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItemsEmpty
import cl.test.feature.searchproducts.presentation.search.SearchAction
import cl.test.feature.searchproducts.presentation.search.SearchProcessor
import cl.test.feature.searchproducts.presentation.search.SearchReducer
import cl.test.feature.searchproducts.presentation.search.SearchResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.DisplayItemResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.GetItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.SearchItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.InitialUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.PressingItemUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.PressingSearchBarUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.DisplayItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.InitialEmptyItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.LoadingUiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

internal class SearchViewModelTest {
    private val processor = mockk<SearchProcessor>()
    private val reducer = mockk<SearchReducer>()
    private val viewModel = SearchViewModel(reducer, processor)

    @Test
    fun `given InitialUIntent, when call GetItemsResult-InProgress, then result LoadingUiState`() =
        runBlocking {
            val result = GetItemsResult.InProgress
            val category = toString()
            stubProcessorAction(
                action = SearchAction.GetItemsAction(category),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = LoadingUiState
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(InitialUIntent(category)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.first()
            assertEquals(resultUiState, LoadingUiState)
        }

    @Test
    fun `given InitialUIntent, when call GetItemsResult-Success, then result DisplayItemsUiState`() =
        runBlocking {
            val items = makeItems()
            val result = GetItemsResult.Success(items.itemsResult)
            val category = toString()
            stubProcessorAction(
                action = SearchAction.GetItemsAction(category),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = DisplayItemsUiState(items.itemsResult)
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(InitialUIntent(category)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), DisplayItemsUiState(items.itemsResult))
        }

    @Test
    fun `given InitialUIntent, when call GetItemsResult-Empty, then result EmptyItemsUiState`() =
        runBlocking {
            val items = makeItemsEmpty()
            val result = GetItemsResult.Success(items.itemsResult)
            val category = toString()
            stubProcessorAction(
                action = SearchAction.GetItemsAction(category),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = InitialEmptyItemsUiState
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(InitialUIntent(category)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), InitialEmptyItemsUiState)
        }

    @Test
    fun `given InitialUIntent, when call GetItemsResult-Error, then result ErrorUiState`() =
        runBlocking {
            val result = GetItemsResult.Error
            val category = toString()
            stubProcessorAction(
                action = SearchAction.GetItemsAction(category),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = ErrorUiState
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(InitialUIntent(category)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), ErrorUiState)
        }

    @Test
    fun `given PressingSearchBarUIntent, when call SearchItemsResult-InProgress, then result LoadingUiState`() =
        runBlocking {
            val result = SearchItemsResult.InProgress
            val category = toString()
            val search = toString()
            stubProcessorAction(
                action = SearchAction.SearchItemsAction(category, search),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = LoadingUiState
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(PressingSearchBarUIntent(category, search)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.first()
            assertEquals(resultUiState, LoadingUiState)
        }

    @Test
    fun `given PressingSearchBarUIntent, when call SearchItemsResult-Success, then result DisplayItemsUiState`() =
        runBlocking {
            val items = makeItems()
            val result = SearchItemsResult.Success(items.itemsResult)
            val category = toString()
            val search = toString()
            stubProcessorAction(
                action = SearchAction.SearchItemsAction(category, search),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = DisplayItemsUiState(items.itemsResult)
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(PressingSearchBarUIntent(category, search)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), DisplayItemsUiState(items.itemsResult))
        }

    @Test
    fun `given PressingSearchBarUIntent, when call SearchItemsResult-Empty, then result EmptyItemsUiState`() =
        runBlocking {
            val result = SearchItemsResult.Empty
            val category = toString()
            val search = toString()
            stubProcessorAction(
                action = SearchAction.SearchItemsAction(category, search),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = InitialEmptyItemsUiState
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(PressingSearchBarUIntent(category, search)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), InitialEmptyItemsUiState)
        }

    @Test
    fun `given PressingSearchBarUIntent, when call SearchItemsResult-Error, then result ErrorUiState`() =
        runBlocking {
            val result = SearchItemsResult.Error
            val category = toString()
            val search = toString()
            stubProcessorAction(
                action = SearchAction.SearchItemsAction(category, search),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = ErrorUiState
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(PressingSearchBarUIntent(category, search)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), ErrorUiState)
        }

    @Test
    fun `given PressingItemUIntent, when call DisplayItemResult-Success, then result DisplayItemsUiState`() =
        runBlocking {
            val item = makeItem()
            val items = makeItems()
            val result = DisplayItemResult.Success(item)
            stubProcessorAction(
                action = SearchAction.DisplayItemAction(item),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = DisplayItemsUiState(items.itemsResult)
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(PressingItemUIntent(item)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), DisplayItemsUiState(items.itemsResult))
        }

    private fun stubProcessorAction(
        action: SearchAction,
        result: SearchResult
    ) {
        coEvery { processor.actionProcessor(action) } returns flow { emit(result) }
    }

    private fun stubReducerReduceWith(
        previousState: SearchUiState,
        result: SearchResult,
        nexUiState: SearchUiState
    ) {
        with(reducer) {
            every { previousState reduceWith result } returns nexUiState
        }
    }
}