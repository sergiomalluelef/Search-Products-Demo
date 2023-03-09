package cl.test.feature.searchproducts.data.remote.model

import cl.test.feature.searchproducts.data.remote.model.Constants.MAX_SIZE
import cl.test.feature.searchproducts.data.remote.model.Constants.SECURE_URL
import cl.test.feature.searchproducts.data.remote.model.Constants.SIZE
import com.google.gson.annotations.SerializedName

internal data class RemotePicture(
    @SerializedName(SIZE)
    val size: String?,
    @SerializedName(SECURE_URL)
    val secureUrl: String?,
    @SerializedName(MAX_SIZE)
    val maxSize: String?,
)