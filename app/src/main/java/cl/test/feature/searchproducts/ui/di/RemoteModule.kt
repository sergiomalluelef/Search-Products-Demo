package cl.test.feature.searchproducts.ui.di

import cl.test.feature.searchproducts.data.remote.SearchProductsRemoteImpl
import cl.test.feature.searchproducts.data.remote.retrofit.SearchProductsWebService
import cl.test.feature.searchproducts.data.remote.source.SearchProductsRemote
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RemoteModule {

    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideSearchProductsWebService(retrofit: Retrofit): SearchProductsWebService {
            return retrofit.create(SearchProductsWebService::class.java)
        }
    }

    @Binds
    fun bindRemote(remoteImpl: SearchProductsRemoteImpl): SearchProductsRemote
}