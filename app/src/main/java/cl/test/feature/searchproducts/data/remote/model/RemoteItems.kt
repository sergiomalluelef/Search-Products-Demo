package cl.test.feature.searchproducts.data.remote.model

import cl.test.feature.searchproducts.data.remote.model.Constants.RESULTS
import com.google.gson.annotations.SerializedName

internal data class RemoteItems(
    @SerializedName(RESULTS) val itemsResult: List<RemoteItem?>?
)
