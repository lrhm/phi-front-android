package xyz.lrhm.phiapp.hilt

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApolloModule {

    @Provides
    @Singleton
    fun providesApolloClient() = ApolloClient.builder()
        .serverUrl("http://192.168.2.5:4000")
        .build()


}