package cl.test.feature.searchproducts.presentation.search.mapper

import cl.test.feature.searchproducts.data.remote.model.RemoteInstallments
import cl.test.feature.searchproducts.factory.SearchProductsFactory.makeRemoteItems
import cl.test.feature.searchproducts.presentation.search.model.Installments
import org.junit.Assert.assertEquals
import org.junit.Test

internal class SearchMapperTest {
    private val mapper = SearchMapper()

    @Test
    fun `given RemoteItems, when toItems, then Items`() {
        val remoteItems = makeRemoteItems()

        val items = with(mapper) { remoteItems.toItems() }

        remoteItems.itemsResult?.zip(items.itemsResult)?.map { (expected, actual) ->
            assertEquals(expected?.originalPrice ?: 0, actual.originalPrice)
            assertEquals(expected?.condition.orEmpty(), actual.condition)
            assertEquals(expected?.thumbnail.orEmpty(), actual.thumbnail)

            asserEqualsRemoteInstallmentsToInstallments(
                expected?.remoteInstallments,
                actual.installments
            )
            assertEquals(expected?.price ?: 0, actual.price)
            assertEquals(expected?.id.orEmpty(), actual.id)
            assertEquals(expected?.officialStoreName.orEmpty(), actual.officialStoreName)
        }
    }

    private fun asserEqualsRemoteInstallmentsToInstallments(
        remoteInstallments: RemoteInstallments?,
        installments: Installments
    ) {
        assertEquals(remoteInstallments?.amount ?: 0.0, installments.amount, 0.0)
        assertEquals(remoteInstallments?.quantity ?: 0, installments.quantity)
        assertEquals(remoteInstallments?.rate ?: 0, installments.rate)
        assertEquals(remoteInstallments?.currencyId.orEmpty(), installments.currencyId)
    }
}