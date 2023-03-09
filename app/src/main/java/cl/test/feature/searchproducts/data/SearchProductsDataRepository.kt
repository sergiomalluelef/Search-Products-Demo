package cl.test.feature.searchproducts.data

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import cl.test.feature.searchproducts.data.remote.source.SearchProductsRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchProductsDataRepository @Inject constructor(
    private val remote: SearchProductsRemote
) {
    fun getItemsByCategory(category: String): Flow<RemoteItems> = flow {
        val response = remote.getItemsByCategory(category)
        emit(response)
    }

    fun getSearchInCategory(category: String, search: String): Flow<RemoteItems> = flow {
        val response = remote.getSearchInCategory(category, search)
        emit(response)
    }

    fun getPictures(id: String): Flow<RemotePictures> = flow {
        val response = remote.getPictures(id)
        emit(response)
    }

    fun getItemDescription(id: String): Flow<RemoteDescription> = flow {
        val response = remote.getItemDescription(id)
        emit(response)
    }
}