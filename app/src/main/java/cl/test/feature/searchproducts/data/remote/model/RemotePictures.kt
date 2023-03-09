package cl.test.feature.searchproducts.data.remote.model

import cl.test.feature.searchproducts.data.remote.model.Constants.PICTURES
import com.google.gson.annotations.SerializedName

internal data class RemotePictures(
    @SerializedName(PICTURES)
    val remotePictures: List<RemotePicture?>?
)