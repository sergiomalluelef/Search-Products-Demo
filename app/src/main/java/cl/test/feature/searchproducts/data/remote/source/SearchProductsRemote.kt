package cl.test.feature.searchproducts.data.remote.source

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.data.remote.model.RemotePictures

internal interface SearchProductsRemote {
    suspend fun getItemsByCategory(category: String): RemoteItems
    suspend fun getSearchInCategory(category: String,search: String): RemoteItems
    suspend fun getItemDescription(id: String): RemoteDescription
    suspend fun getPictures(id: String): RemotePictures
}