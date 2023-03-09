package cl.test.feature.searchproducts.data.remote.model

import cl.test.feature.searchproducts.data.remote.model.Constants.CONDITION
import cl.test.feature.searchproducts.data.remote.model.Constants.ID
import cl.test.feature.searchproducts.data.remote.model.Constants.INSTALLMENTS
import cl.test.feature.searchproducts.data.remote.model.Constants.OFFICIAL_STORE_NAME
import cl.test.feature.searchproducts.data.remote.model.Constants.ORIGINAL_PRICE
import cl.test.feature.searchproducts.data.remote.model.Constants.PRICE
import cl.test.feature.searchproducts.data.remote.model.Constants.THUMBNAIL
import cl.test.feature.searchproducts.data.remote.model.Constants.TITLE
import com.google.gson.annotations.SerializedName

internal data class RemoteItem(
    @SerializedName(ORIGINAL_PRICE)
    val originalPrice: Int?,
    @SerializedName(CONDITION)
    val condition: String?,
    @SerializedName(THUMBNAIL)
    val thumbnail: String?,
    @SerializedName(INSTALLMENTS)
    val remoteInstallments: RemoteInstallments?,
    @SerializedName(PRICE)
    val price: Int?,
    @SerializedName(ID)
    val id: String?,
    @SerializedName(TITLE)
    val title: String?,
    @SerializedName(OFFICIAL_STORE_NAME)
    val officialStoreName: String?
)