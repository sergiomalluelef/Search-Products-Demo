package cl.test.feature.searchproducts.data.remote

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import cl.test.feature.searchproducts.data.remote.retrofit.SearchProductsWebService
import cl.test.feature.searchproducts.data.remote.source.SearchProductsRemote
import javax.inject.Inject

internal class SearchProductsRemoteImpl @Inject constructor(
    private val api: SearchProductsWebService
) : SearchProductsRemote {
    override suspend fun getItemsByCategory(category: String): RemoteItems =
        api.getItemsByCategory(category)
    override suspend fun getSearchInCategory(category: String, search: String): RemoteItems = api.getSearchInCategory(category, search)
    override suspend fun getItemDescription(id: String): RemoteDescription = api.getItemDescription(id)
    override suspend fun getPictures(id: String): RemotePictures = api.getPictures(id)
}