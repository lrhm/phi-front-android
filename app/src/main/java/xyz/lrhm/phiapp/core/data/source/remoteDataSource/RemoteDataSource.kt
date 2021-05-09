package xyz.lrhm.phiapp.core.data.source.remoteDataSource

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toFlow
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.request.RequestHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import xyz.lrhm.APIQuery
import xyz.lrhm.LoginQuery
import xyz.lrhm.SubmitEvaluationMutation
import xyz.lrhm.phiapp.core.data.model.ResultOf
import xyz.lrhm.phiapp.core.util.CacheUtil
import xyz.lrhm.type.EvaluationInput
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    val apolloClient: ApolloClient,
    val cacheUtil: CacheUtil
) {

    val user = MutableLiveData<APIQuery.User>()

    var cachedUser: APIQuery.User? = null

    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {
        val response = try {

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

    suspend fun watchUser() {

        val token = cacheUtil.getToken()
        apolloClient.query(APIQuery()).toBuilder()
            .responseFetcher(ApolloResponseFetchers.CACHE_FIRST).requestHeaders(
                RequestHeaders.builder().addHeader("Authorization", token).build()
            ).build().watcher().toFlow().collect {


                if (it.data?.user != null)

                    withContext(Dispatchers.Main) {
                        user.value = it.data!!.user!!
                        cachedUser = it.data!!.user
                    }
            }
    }

    suspend fun getUser() = withContext(Dispatchers.IO) {


        val token = cacheUtil.getToken()
        val response = try {
            apolloClient.query(APIQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).requestHeaders(
                    RequestHeaders.builder().addHeader("Authorization", token).build()
                ).build().await()
        } catch (e: ApolloException) {
            // handle protocol errors
            return@withContext ResultOf.Error(e)
        }


        Timber.d("response is $response")

        CoroutineScope(Dispatchers.IO).launch {
            watchUser()
        }

        val data = response.data?.user

        if (data == null || response.hasErrors()) {
            return@withContext ResultOf.Error(Exception("error"))

        }

        withContext(Dispatchers.Main) {
            cachedUser = data!!
        }


        return@withContext ResultOf.Success(data)

    }


    suspend fun submitEvaluation(input: EvaluationInput) = withContext(Dispatchers.IO) {

        val token = cacheUtil.getToken()

        val response = try {

            apolloClient.mutate(SubmitEvaluationMutation(input.toInput()))
                .toBuilder()
                .requestHeaders(
                    RequestHeaders.builder().addHeader("Authorization", token).build()
                ).build()
                .await()
        } catch (e: ApolloException) {
            // handle protocol errors


            Timber.e(e)

            return@withContext ResultOf.Error(e)
        }

        val data = response.data?.submitEvaluation?.evaluation
        if (data == null || response.hasErrors()) {
            // handle application errors
            Timber.d("${response.errors}")
            return@withContext ResultOf.Error(Exception("error"))
        }

        return@withContext ResultOf.Success(data)
    }


}