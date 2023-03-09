package cl.test.feature.searchproducts.presentation.detail.mapper

import cl.test.feature.searchproducts.factory.DetailFactory.makeRemoteDescription
import cl.test.feature.searchproducts.factory.DetailFactory.makeRemotePictures
import org.junit.Assert.assertEquals
import org.junit.Test

internal class DetailMapperTest {
    private val mapper = DetailMapper()

    @Test
    fun `given RemoteDescription, when toDescription, then Description`() {
        val remoteDescription = makeRemoteDescription()

        val description = with(mapper) { remoteDescription.toDescription() }

        assertEquals(remoteDescription.text.orEmpty(), description.text)
        assertEquals(remoteDescription.plainText.orEmpty(), description.plainText)
    }

    @Test
    fun `given RemotePictures, when toPictures, then Pictures`() {
        val remotePictures = makeRemotePictures()

        val pictures = with(mapper) { remotePictures.toPictures() }

        remotePictures.remotePictures?.zip(pictures.pictures)?.map { (expected, actual) ->
            assertEquals(expected?.maxSize.orEmpty(), actual.maxSize)
            assertEquals(expected?.secureUrl.orEmpty(), actual.secureUrl)
            assertEquals(expected?.size.orEmpty(), actual.size)
        }
    }
}