package cl.test.feature.searchproducts.presentation.detail

import cl.test.feature.searchproducts.data.SearchProductsDataRepository
import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import cl.test.feature.searchproducts.factory.DetailFactory.makeDescription
import cl.test.feature.searchproducts.factory.DetailFactory.makePictures
import cl.test.feature.searchproducts.factory.DetailFactory.makeRemoteDescription
import cl.test.feature.searchproducts.factory.DetailFactory.makeRemotePictures
import cl.test.feature.searchproducts.presentation.detail.DetailAction.GetDetailAction
import cl.test.feature.searchproducts.presentation.detail.DetailResult.GetDetailResult
import cl.test.feature.searchproducts.presentation.detail.mapper.DetailMapper
import cl.test.feature.searchproducts.presentation.detail.model.Description
import cl.test.feature.searchproducts.presentation.detail.model.Pictures
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

internal class DetailProcessorTest {
    private val repository = mockk<SearchProductsDataRepository>(relaxed = true)
    private val mapper = mockk<DetailMapper>()
    private val processor = DetailProcessor(repository, mapper)

    @Test
    fun `given GetDetailAction, when call actionProcessor, then result Success`() = runBlocking {
        val remotePictures = makeRemotePictures()
        val remoteDescription = makeRemoteDescription()
        val pictures = makePictures()
        val description = makeDescription()
        val id = Random.toString()
        stubGetPictures(remotePictures)
        stubGetDescription(remoteDescription)

        stubRemotePicturesToPictures(
            remotePictures = remotePictures,
            pictures = pictures
        )
        stubRemoteDescriptionToDescription(
            remoteDescription = remoteDescription,
            description = description
        )

        val results = processor.actionProcessor(GetDetailAction(id)).toList()

        assertEquals(
            results.first(),
            GetDetailResult.InProgress
        )
        assertEquals(
            results.last(),
            GetDetailResult.Success(
                description = description.plainText,
                pictures = pictures.pictures,
            )
        )
    }

    @Test
    fun `given GetDetailAction, when call actionProcessor, then result Error`() = runBlocking {
        val id = Random.toString()
        stubGetPicturesError(Throwable())

        val results = processor.actionProcessor(GetDetailAction(id)).toList()

        assertEquals(
            results.first(),
            GetDetailResult.InProgress
        )
        assertEquals(
            results.last(),
            GetDetailResult.Error
        )

    }

    private fun stubGetPictures(remotePictures: RemotePictures) {
        coEvery { repository.getPictures(any()) } returns flow { emit(remotePictures) }
    }

    private fun stubGetPicturesError(error: Throwable) {
        coEvery { repository.getPictures(any()) } returns flow { throw error }
    }

    private fun stubGetDescription(remoteDescription: RemoteDescription) {
        coEvery { repository.getItemDescription(any()) } returns flow { emit(remoteDescription) }
    }

    private fun stubRemoteDescriptionToDescription(
        remoteDescription: RemoteDescription,
        description: Description
    ) {
        every { with(mapper) { remoteDescription.toDescription() } } returns description
    }

    private fun stubRemotePicturesToPictures(
        remotePictures: RemotePictures,
        pictures: Pictures
    ) {
        every { with(mapper) { remotePictures.toPictures() } } returns pictures
    }
}