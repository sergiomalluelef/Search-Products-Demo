package cl.test.feature.searchproducts.data.remote

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import cl.test.feature.searchproducts.data.remote.retrofit.SearchProductsWebService
import cl.test.feature.searchproducts.factory.DetailFactory.makeRemoteDescription
import cl.test.feature.searchproducts.factory.DetailFactory.makeRemotePictures
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeRemoteItems
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

internal class SearchProductsFactoryRemoteImplTest {
    private val webService = mockk<SearchProductsWebService>()
    private val remoteImpl = SearchProductsRemoteImpl(webService)

    @Test
    fun `when call getItemsByCategory then return RemoteItems`(): Unit = runBlocking {
        val remoteItems = makeRemoteItems()
        stubGetItemsByCategory(remoteItems)

        val category = Random().toString()
        val result = remoteImpl.getItemsByCategory(category)

        assertEquals(remoteItems, result)
        coEvery { webService.getItemsByCategory(category) }
    }

    @Test
    fun `when call getSearchInCategory then return RemoteItems`(): Unit = runBlocking {
        val remoteItems = makeRemoteItems()
        stubGetSearchInCategory(remoteItems)

        val category = Random().toString()
        val search = Random().toString()
        val result = remoteImpl.getSearchInCategory(category, search)

        assertEquals(remoteItems, result)
        coEvery { webService.getSearchInCategory(category, search) }
    }

    @Test
    fun `when call getItemDescription then return RemoteDescription`(): Unit = runBlocking {
        val remoteDescription = makeRemoteDescription()
        stubGetItemDescription(remoteDescription)

        val id = Random().toString()
        val result = remoteImpl.getItemDescription(id)

        assertEquals(remoteDescription, result)
        coEvery { webService.getItemDescription(id) }
    }

    @Test
    fun `when call getPictures then return RemotePictures`(): Unit = runBlocking {
        val remotePictures = makeRemotePictures()
        stubGetPictures(remotePictures)

        val id = Random().toString()
        val result = remoteImpl.getPictures(id)

        assertEquals(remotePictures, result)
        coEvery { webService.getPictures(id) }
    }

    private fun stubGetItemsByCategory(remoteItems: RemoteItems) {
        coEvery { webService.getItemsByCategory(any()) } returns remoteItems
    }

    private fun stubGetSearchInCategory(remoteItems: RemoteItems) {
        coEvery { webService.getSearchInCategory(any(), any()) } returns remoteItems
    }

    private fun stubGetItemDescription(remoteDescription: RemoteDescription) {
        coEvery { webService.getItemDescription(any()) } returns remoteDescription
    }

    private fun stubGetPictures(remotePictures: RemotePictures) {
        coEvery { webService.getPictures(any()) } returns remotePictures
    }
}