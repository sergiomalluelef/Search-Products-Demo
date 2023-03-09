package cl.test.feature.searchproducts.factory

import cl.test.feature.searchproducts.data.remote.model.RemoteInstallments
import cl.test.feature.searchproducts.data.remote.model.RemoteItem
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.presentation.search.model.Installments
import cl.test.feature.searchproducts.presentation.search.model.Item
import cl.test.feature.searchproducts.presentation.search.model.Items
import java.util.*

internal object SearchProductsFactory {

    fun makeRemoteItems() = RemoteItems(
        itemsResult = makeRemoteItemsList(3)
    )

    fun makeRemoteItemsEmpty() = RemoteItems(
        itemsResult = emptyList()
    )

    fun makeRemoteItemsList(count: Int): List<RemoteItem> =
        (1..count).map {
            makeRemoteItem()
        }

    fun makeRemoteItem() = RemoteItem(
        originalPrice = Random().nextInt(),
        condition = Random().toString(),
        thumbnail = Random().toString(),
        remoteInstallments = makeRemoteInstallments(),
        price = Random().nextInt(),
        id = Random().toString(),
        title = Random().toString(),
        officialStoreName = Random().toString(),
    )

    fun makeRemoteInstallments() = RemoteInstallments(
        amount = Random().nextDouble(),
        quantity = Random().nextInt(),
        rate = Random().nextInt(),
        currencyId = Random().toString(),
    )

    fun makeItems() = Items(
        itemsResult = makeItemsList(3)
    )

    fun makeItemsEmpty() = Items(
        itemsResult = emptyList()
    )

    fun makeItemsList(count: Int): List<Item> =
        (1..count).map {
            makeItem()
        }

    fun makeItem() = Item(
        originalPrice = Random().nextInt(),
        condition = Random().toString(),
        thumbnail = Random().toString(),
        installments = makeInstallments(),
        price = Random().nextInt(),
        id = Random().toString(),
        title = Random().toString(),
        officialStoreName = Random().toString(),
    )

    fun makeInstallments() = Installments(
        amount = Random().nextDouble(),
        quantity = Random().nextInt(),
        rate = Random().nextInt(),
        currencyId = Random().toString(),
    )
}