package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItem
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItems
import cl.test.feature.searchproducts.presentation.search.SearchResult.GetItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.SearchItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchUiState.DisplayItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.InitialEmptyItemsUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.LoadingUiState
import io.mockk.justRun
import org.junit.Test

internal class SearchReducerTest {
    private val reducer = SearchReducer()

    @Test
    fun `given LoadingUiState, with Result GetItemsResult-InProgress, when reduce return LoadingUiState`() {
        val previousUiState = LoadingUiState
        val result = GetItemsResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test
    fun `given LoadingUiState, with Result GetItemsResult-Empty, when reduce return EmptyItemsUiState`() {
        val previousUiState = LoadingUiState
        val result = GetItemsResult.Empty

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is InitialEmptyItemsUiState)
    }

    @Test
    fun `given LoadingUiState, with Result GetItemsResult-Error, when reduce return ErrorUiState`() {
        val previousUiState = LoadingUiState
        val result = GetItemsResult.Error

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is ErrorUiState)
    }

    @Test
    fun `given LoadingUiState, with Result GetItemsResult-Success, when reduce return DisplayItemsUiState`() {
        val previousUiState = LoadingUiState
        val items = makeItems()
        val result = GetItemsResult.Success(items.itemsResult)

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is DisplayItemsUiState)
    }

    @Test
    fun `given LoadingUiState, with Result SearchItemsResult-Empty, when reduce return EmptyItemsUiState`() {
        val previousUiState = LoadingUiState
        val result = SearchItemsResult.Empty

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is InitialEmptyItemsUiState)
    }

    @Test
    fun `given LoadingUiState, with Result SearchItemsResult-Error, when reduce return ErrorUiState`() {
        val previousUiState = LoadingUiState
        val result = SearchItemsResult.Error

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is ErrorUiState)
    }

    @Test
    fun `given LoadingUiState, with Result SearchItemsResult-Success, when reduce return DisplayItemsUiState`() {
        val previousUiState = LoadingUiState
        val items = makeItems()
        val result = SearchItemsResult.Success(items.itemsResult)

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is DisplayItemsUiState)
    }

    @Test(expected = RuntimeException::class)
    fun `given LoadingUiState, with any result, when throw exception`() {
        val previousUiState = LoadingUiState
        val result = SearchItemsResult.InProgress
        stubThrowRuntimeException(result.toString())
        with(reducer) { previousUiState reduceWith result }
    }

    @Test
    fun `given DisplayItemsUiState, with Result GetItemsResult-InProgress, when reduce return LoadingUiState`() {
        val items = makeItems()
        val previousUiState = DisplayItemsUiState(items.itemsResult)
        val result = GetItemsResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test
    fun `given DisplayItemsUiState, with Result SearchItemsResult-InProgress, when reduce return LoadingUiState`() {
        val items = makeItems()
        val previousUiState = DisplayItemsUiState(items.itemsResult)
        val result = SearchItemsResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test
    fun `given DisplayItemsUiState, with Result DisplayItemResult-Success, when reduce return LoadingUiState`() {
        val items = makeItems()
        val item = makeItem()
        val previousUiState = DisplayItemsUiState(items.itemsResult)
        val result = SearchResult.DisplayItemResult.Success(item)

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is DisplayItemsUiState)
    }

    @Test(expected = RuntimeException::class)
    fun `given DisplayItemsUiState, with any result, when throw exception`() {
        val previousUiState = LoadingUiState
        val result = SearchItemsResult.Empty
        stubThrowRuntimeException(result.toString())
        with(reducer) { previousUiState reduceWith result }
    }

    @Test
    fun `given EmptyItemsUiState, with Result GetItemsResult-InProgress, when reduce return LoadingUiState`() {
        val previousUiState = InitialEmptyItemsUiState
        val result = GetItemsResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test
    fun `given EmptyItemsUiState, with Result SearchItemsResult-InProgress, when reduce return LoadingUiState`() {
        val previousUiState = InitialEmptyItemsUiState
        val result = SearchItemsResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test(expected = RuntimeException::class)
    fun `given EmptyItemsUiState, with any result, when throw exception`() {
        val previousUiState = InitialEmptyItemsUiState
        val result = SearchItemsResult.Empty
        stubThrowRuntimeException(result.toString())
        with(reducer) { previousUiState reduceWith result }
    }

    @Test
    fun `given ErrorUiState, with Result GetItemsResult-InProgress, when reduce return LoadingUiState`() {
        val previousUiState = ErrorUiState
        val result = GetItemsResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test
    fun `given ErrorUiState, with Result SearchItemsResult-InProgress, when reduce return LoadingUiState`() {
        val previousUiState = ErrorUiState
        val result = SearchItemsResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test(expected = RuntimeException::class)
    fun `given ErrorUiState, with any result, when throw exception`() {
        val previousUiState = ErrorUiState
        val result = SearchItemsResult.Empty
        stubThrowRuntimeException(result.toString())
        with(reducer) { previousUiState reduceWith result }
    }

    private fun stubThrowRuntimeException(error: String) {
        justRun { throw RuntimeException(error) }
    }
}