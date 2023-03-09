package cl.test.feature.searchproducts.factory

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemotePicture
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import cl.test.feature.searchproducts.presentation.detail.model.Description
import cl.test.feature.searchproducts.presentation.detail.model.DetailState
import cl.test.feature.searchproducts.presentation.detail.model.Picture
import cl.test.feature.searchproducts.presentation.detail.model.Pictures
import java.util.*

internal object DetailFactory {

    fun makeRemoteDescription() = RemoteDescription(
        plainText = Random().toString(),
        text = Random().toString(),
    )

    fun makeRemotePictures() = RemotePictures(
        remotePictures = makeRemotePictureList(3),
    )

    fun makeRemotePictureList(count: Int): List<RemotePicture> =
        (1..count).map {
            makeRemotePicture()
        }

    fun makeRemotePicture() = RemotePicture(
        size = Random().toString(),
        secureUrl = Random().toString(),
        maxSize = Random().toString(),
    )

    fun makePictures() = Pictures(
        pictures = makePictureList(3),
    )

    fun makePictureList(count: Int): List<Picture> =
        (1..count).map {
            makePicture()
        }

    fun makePicture() = Picture(
        size = Random().toString(),
        secureUrl = Random().toString(),
        maxSize = Random().toString(),
    )

    fun makeDescription() = Description(
        plainText = Random().toString(),
        text = Random().toString(),
    )

    fun makeDetailState() = DetailState(
        description = Random().toString(),
        pictures = makePictureList(3),
    )
}