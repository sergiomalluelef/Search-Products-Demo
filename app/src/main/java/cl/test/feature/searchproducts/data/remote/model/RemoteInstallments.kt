package cl.test.feature.searchproducts.data.remote.model

import cl.test.feature.searchproducts.data.remote.model.Constants.AMOUNT
import cl.test.feature.searchproducts.data.remote.model.Constants.CURRENCY_ID
import cl.test.feature.searchproducts.data.remote.model.Constants.QUANTITY
import cl.test.feature.searchproducts.data.remote.model.Constants.RATE
import com.google.gson.annotations.SerializedName

internal data class RemoteInstallments(
    @SerializedName(AMOUNT)
    val amount: Double?,
    @SerializedName(QUANTITY)
    val quantity: Int?,
    @SerializedName(RATE)
    val rate: Int?,
    @SerializedName(CURRENCY_ID)
    val currencyId: String?
)