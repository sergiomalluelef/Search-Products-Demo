package cl.test.feature.searchproducts.presentation.search

import cl.test.feature.searchproducts.data.SearchProductsDataRepository
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItem
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItems
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeItemsEmpty
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeRemoteItems
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeRemoteItemsEmpty
import cl.test.feature.searchproducts.presentation.search.SearchResult.DisplayItemResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.GetItemsResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.SearchItemsResult
import cl.test.feature.searchproducts.presentation.search.mapper.SearchMapper
import cl.test.feature.searchproducts.presentation.search.model.Items
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

internal class SearchProcessorTest {
    private val repository = mockk<SearchProductsDataRepository>(relaxed = true)
    private val mapper = mockk<SearchMapper>()
    private val processor = SearchProcessor(repository, mapper)

    @Test
    fun `given GetItemsAction, when call actionProcessor, then result Success`() = runBlocking {
        val remoteItems = makeRemoteItems()
        val items = makeItems()
        val category = Random.toString()
        stubGetItemsByCategory(remoteItems)

        stubRemoteItemsToItems(
            remoteItems = remoteItems,
            items = items
        )

        val results = processor.actionProcessor(SearchAction.GetItemsAction(category)).toList()

        assertEquals(
            results.first(),
            GetItemsResult.InProgress
        )
        assertEquals(
            results.last(),
            GetItemsResult.Success(
                items = items.itemsResult
            )
        )
    }

    @Test
    fun `given GetItemsAction, when call actionProcessor, then result Empty`() = runBlocking {
        val remoteItems = makeRemoteItemsEmpty()
        val items = makeItemsEmpty()
        val category = Random.toString()
        stubGetItemsByCategory(remoteItems)

        stubRemoteItemsToItems(
            remoteItems = remoteItems,
            items = items
        )

        val results = processor.actionProcessor(SearchAction.GetItemsAction(category)).toList()

        assertEquals(
            results.first(),
            GetItemsResult.InProgress
        )
        assertEquals(
            results.last(),
            GetItemsResult.Empty
        )
    }

    @Test
    fun `given GetItemsAction, when call actionProcessor, then result Error`() = runBlocking {
        val category = Random.toString()
        stubGetItemsByCategoryError(Throwable())

        val results = processor.actionProcessor(SearchAction.GetItemsAction(category)).toList()

        assertEquals(
            results.first(),
            GetItemsResult.InProgress
        )
        assertEquals(
            results.last(),
            GetItemsResult.Error
        )
    }

    @Test
    fun `given SearchItemsAction, when call actionProcessor, then result Success`() = runBlocking {
        val remoteItems = makeRemoteItems()
        val items = makeItems()
        val category = Random.toString()
        val search = Random.toString()
        stubGetSearchInCategory(remoteItems)

        stubRemoteItemsToItems(
            remoteItems = remoteItems,
            items = items
        )

        val results =
            processor.actionProcessor(SearchAction.SearchItemsAction(category, search)).toList()

        assertEquals(
            results.first(),
            SearchItemsResult.InProgress
        )
        assertEquals(
            results.last(),
            SearchItemsResult.Success(
                items = items.itemsResult
            )
        )
    }

    @Test
    fun `given SearchItemsAction, when call actionProcessor, then result Empty`() = runBlocking {
        val remoteItems = makeRemoteItemsEmpty()
        val items = makeItemsEmpty()
        val category = Random.toString()
        val search = Random.toString()
        stubGetSearchInCategory(remoteItems)

        stubRemoteItemsToItems(
            remoteItems = remoteItems,
            items = items
        )

        val results =
            processor.actionProcessor(SearchAction.SearchItemsAction(category, search)).toList()

        assertEquals(
            results.first(),
            SearchItemsResult.InProgress
        )
        assertEquals(
            results.last(),
            SearchItemsResult.Empty
        )
    }

    @Test
    fun `given SearchItemsAction, when call actionProcessor, then result Error`() = runBlocking {
        val category = Random.toString()
        val search = Random.toString()
        stubGetSearchInCategoryError(Throwable())

        val results =
            processor.actionProcessor(SearchAction.SearchItemsAction(category, search)).toList()

        assertEquals(
            results.first(),
            SearchItemsResult.InProgress
        )
        assertEquals(
            results.last(),
            SearchItemsResult.Error
        )
    }

    @Test
    fun `given DisplayItemAction, when call actionProcessor, then result Success`() = runBlocking {
        val item = makeItem()

        val results =
            processor.actionProcessor(SearchAction.DisplayItemAction(item)).toList()

        assertEquals(
            results.first(),
            DisplayItemResult.Success(item)
        )
    }

    private fun stubGetItemsByCategory(remoteItems: RemoteItems) {
        coEvery { repository.getItemsByCategory(any()) } returns flow { emit(remoteItems) }
    }

    private fun stubGetSearchInCategory(remoteItems: RemoteItems) {
        coEvery { repository.getSearchInCategory(any(), any()) } returns flow { emit(remoteItems) }
    }

    private fun stubGetItemsByCategoryError(error: Throwable) {
        coEvery { repository.getItemsByCategory(any()) } returns flow { throw error }
    }

    private fun stubGetSearchInCategoryError(error: Throwable) {
        coEvery { repository.getSearchInCategory(any(), any()) } returns flow { throw error }
    }

    private fun stubRemoteItemsToItems(
        remoteItems: RemoteItems,
        items: Items
    ) {
        every { with(mapper) { remoteItems.toItems() } } returns items
    }
}