package xyz.lrhm.phiapp.core.data.source.remoteDataSource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.request.RequestHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import xyz.lrhm.GetUserQuery
import xyz.lrhm.LoginQuery
import xyz.lrhm.phiapp.core.data.model.ResultOf
import xyz.lrhm.phiapp.core.util.CacheUtil
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    val apolloClient: ApolloClient,
    val cacheUtil: CacheUtil
) {

    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {
        val response = try {

//            apolloClient.query(LoginQuery(username,password)).toBuilder().requestHeaders()
            apolloClient.query(LoginQuery(username, password)).await()
        } catch (e: ApolloException) {
            // handle protocol errors
            return@withContext ResultOf.Error(e)
        }

        val launch = response.data?.tokenPayload
        if (launch == null || response.hasErrors()) {
            // handle application errors
            return@withContext ResultOf.Error(Exception("error"))
        }
        cacheUtil.storeToken(launch.token)
        return@withContext ResultOf.Success(launch)
    }


    suspend fun getUser() = withContext(Dispatchers.IO) {

        val token = cacheUtil.getToken()
        val response = try {
            apolloClient.query(GetUserQuery()).toBuilder().responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).requestHeaders(
                RequestHeaders.builder().addHeader("Authorization", token).build()
            ).build().await()
        } catch (e: ApolloException) {
            // handle protocol errors
            return@withContext ResultOf.Error(e)
        }


        Timber.d("response is $response")


        val data = response.data?.user

        if (data == null || response.hasErrors()) {
            return@withContext ResultOf.Error(Exception("error"))

        }
        return@withContext ResultOf.Success(data)

    }

}