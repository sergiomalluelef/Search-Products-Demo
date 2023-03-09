package cl.test.feature.searchproducts.data.remote.model

import cl.test.feature.searchproducts.data.remote.model.Constants.PLAIN_TEXT
import cl.test.feature.searchproducts.data.remote.model.Constants.TEXT
import com.google.gson.annotations.SerializedName

internal data class RemoteDescription(
    @SerializedName(PLAIN_TEXT)
    val plainText: String?,
    @SerializedName(TEXT)
    val text: String?
)