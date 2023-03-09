package cl.test.feature.searchproducts.presentation.search.mapper

import cl.test.feature.searchproducts.data.remote.model.RemoteInstallments
import cl.test.feature.searchproducts.data.remote.model.RemoteItem
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.presentation.search.model.Installments
import cl.test.feature.searchproducts.presentation.search.model.Item
import cl.test.feature.searchproducts.presentation.search.model.Items
import cl.test.feature.searchproducts.presentation.search.model.orEmpty
import javax.inject.Inject

internal class SearchMapper @Inject constructor() {

    fun RemoteItems.toItems(): Items = Items(
        itemsResult = itemsResult?.toItemList().orEmpty()
    )

    private fun List<RemoteItem?>.toItemList(): List<Item> =
        mapNotNull { it?.toItem() }

    private fun RemoteItem.toItem(): Item = Item(
        originalPrice = originalPrice ?: 0,
        condition = condition.orEmpty(),
        thumbnail = thumbnail.orEmpty(),
        installments = remoteInstallments?.toInstallments().orEmpty(),
        price = price ?: 0,
        id = id.orEmpty(),
        title = title.orEmpty(),
        officialStoreName = officialStoreName.orEmpty(),
    )

    private fun RemoteInstallments.toInstallments(): Installments = Installments(
        amount = amount ?: 0.0,
        quantity = quantity ?: 0,
        rate = rate ?: 0,
        currencyId = currencyId.orEmpty(),
    )

}