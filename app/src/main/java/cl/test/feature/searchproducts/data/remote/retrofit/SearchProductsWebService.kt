package cl.test.feature.searchproducts.data.remote.retrofit

import cl.test.feature.searchproducts.data.remote.model.RemoteDescription
import cl.test.feature.searchproducts.data.remote.model.RemoteItems
import cl.test.feature.searchproducts.data.remote.model.RemotePictures
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface SearchProductsWebService {
    @GET("sites/MLC/search")
    suspend fun getItemsByCategory(@Query("category") category: String): RemoteItems

    @GET("sites/MLC/search")
    suspend fun getSearchInCategory(
        @Query("category") category: String,
        @Query("q") search: String
    ): RemoteItems

    @GET("items/{id}")
    suspend fun getPictures(
        @Path("id") id: String,
    ): RemotePictures

    @GET("items/{id}/description")
    suspend fun getItemDescription(
        @Path("id") id: String,
    ): RemoteDescription
}