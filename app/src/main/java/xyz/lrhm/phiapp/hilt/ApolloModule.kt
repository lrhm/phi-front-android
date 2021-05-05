package xyz.lrhm.phiapp.hilt

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCache
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApolloModule {


    @Provides
    @Singleton
    fun providesCacheKeyResolver():CacheKeyResolver{

        return object : CacheKeyResolver() {
            override fun fromFieldRecordSet(field: ResponseField, recordSet: Map<String, Any>): CacheKey {
                // Retrieve the id from the object itself
                if(recordSet.containsKey("id"))
                    return CacheKey.from(recordSet["id"] as String)
                return CacheKey.NO_KEY
            }

            override fun fromFieldArguments(field: ResponseField, variables: Operation.Variables): CacheKey {
                // Retrieve the id from the field arguments.
                // In the example, this allows to know that `author(id: "author1")` will retrieve `author1`
                // That sounds straightforward but without this, the cache would have no way of finding the id before executing the request on the
                // network which is what we want to avoid
                if(field.resolveArgument("id", variables) != null)
                    return CacheKey.from(field.resolveArgument("id", variables) as String)
                return CacheKey.NO_KEY
            }
        }
    }

    @Provides
    @Singleton
    fun providesApolloSQL(@ApplicationContext context: Context) = SqlNormalizedCacheFactory(context, "apollo.db")

    @Provides
    @Singleton
    fun providesNormilizedCache(sqlNormalizedCacheFactory: SqlNormalizedCacheFactory): NormalizedCacheFactory<LruNormalizedCache> {

        return LruNormalizedCacheFactory(
            EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build()
        ).chain(sqlNormalizedCacheFactory)
    }
    @Provides
    @Singleton
    fun providesApolloClient(normalizedCache: NormalizedCacheFactory<LruNormalizedCache>, cacheKeyResolver: CacheKeyResolver) = ApolloClient.builder()
        .serverUrl("http://192.168.2.5:4000")
        .normalizedCache(normalizedCache, cacheKeyResolver)
        .build()



}