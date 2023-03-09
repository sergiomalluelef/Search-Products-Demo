package cl.test.feature.searchproducts.data

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import cl.test.feature.searchproducts.data.remote.source.SearchProductsRemote
import cl.test.feature.searchproducts.factory.DetailFactory.makeRemoteDescription
import cl.test.feature.searchproducts.factory.DetailFactory.makeRemotePictures
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeRemoteItems
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Random

internal class SearchProductsDataRepositoryTest {
    private val remote = mockk<SearchProductsRemote>()
    private val repository = SearchProductsDataRepository(remote)

    @Test
    fun `when getItemsByCategory, then call remote`(): Unit = runBlocking {
        val remoteItems = makeRemoteItems()
        stubGetItemsByCategory(remoteItems)

        val category = Random().toString()
        val flow = repository.getItemsByCategory(category)

        flow.collect { result ->
            assertEquals(remoteItems, result)
        }

        coEvery { remote.getItemsByCategory(category) }
    }

    @Test
    fun `when getSearchInCategory, then call RemoteItems`(): Unit = runBlocking {
        val remoteItems = makeRemoteItems()
        stubGetSearchInCategory(remoteItems)

        val category = Random().toString()
        val search = Random().toString()
        val flow = repository.getSearchInCategory(category, search)

        flow.collect { result ->
            assertEquals(remoteItems, result)
        }

        coEvery { remote.getSearchInCategory(category, search) }
    }

    @Test
    fun `when getPictures, then call RemotePictures`(): Unit = runBlocking {
        val remotePictures = makeRemotePictures()
        stubGetPictures(remotePictures)

        val id = Random().toString()
        val flow = repository.getPictures(id)

        flow.collect { result ->
            assertEquals(remotePictures, result)
        }

        coEvery { remote.getPictures(id) }
    }

    @Test
    fun `when getItemDescription, then call RemoteDescription`(): Unit = runBlocking {
        val remoteDescription = makeRemoteDescription()
        stubGetItemDescription(remoteDescription)

        val id = Random().toString()
        val flow = repository.getItemDescription(id)

        flow.collect { result ->
            assertEquals(remoteDescription, result)
        }

        coEvery { remote.getItemDescription(id) }
    }

    private fun stubGetItemsByCategory(remoteItems: RemoteItems) {
        coEvery { remote.getItemsByCategory(any()) } returns remoteItems
    }

    private fun stubGetSearchInCategory(remoteItems: RemoteItems) {
        coEvery { remote.getSearchInCategory(any(), any()) } returns remoteItems
    }

    private fun stubGetItemDescription(remoteDescription: RemoteDescription) {
        coEvery { remote.getItemDescription(any()) } returns remoteDescription
    }

    private fun stubGetPictures(remotePictures: RemotePictures) {
        coEvery { remote.getPictures(any()) } returns remotePictures
    }
}