package xyz.lrhm.phiapp.hilt

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ApolloModule {

    @Provides
    fun providesApolloClient() = ApolloClient.builder()
        .serverUrl("http://localhost:4000")
        .build()


}