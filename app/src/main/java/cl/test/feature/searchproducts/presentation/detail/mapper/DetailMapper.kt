package cl.test.feature.searchproducts.presentation.detail.mapper

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemotePicture
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import cl.test.feature.searchproducts.presentation.detail.model.Description
import cl.test.feature.searchproducts.presentation.detail.model.Picture
import cl.test.feature.searchproducts.presentation.detail.model.Pictures
import javax.inject.Inject

internal class DetailMapper @Inject constructor() {

    fun RemoteDescription.toDescription(): Description = Description(
        plainText = plainText.orEmpty(),
        text = text.orEmpty(),
    )

    fun RemotePictures.toPictures(): Pictures = Pictures(
        pictures = remotePictures?.toPictureList().orEmpty(),
    )

    private fun List<RemotePicture?>.toPictureList(): List<Picture> = mapNotNull {
        it?.toPicture()
    }

    private fun RemotePicture.toPicture(): Picture = Picture(
        size = size.orEmpty(),
        secureUrl = secureUrl.orEmpty(),
        maxSize = maxSize.orEmpty()
    )
}