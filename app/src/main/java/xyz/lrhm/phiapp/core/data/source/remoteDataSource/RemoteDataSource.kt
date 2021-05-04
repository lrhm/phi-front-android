package xyz.lrhm.phiapp.core.data.source.remoteDataSource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.lrhm.LoginQuery
import xyz.lrhm.phiapp.core.data.model.ResultOf
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    val apolloClient: ApolloClient
) {

  suspend  fun login(username:String, password:String) = withContext(Dispatchers.IO) {
        val response = try {
            apolloClient.query(LoginQuery(username,password)).await()
        } catch (e: ApolloException) {
            // handle protocol errors
            return@withContext ResultOf.Error(e)
        }

        val launch = response.data?.tokenPayload
        if (launch == null || response.hasErrors()) {
            // handle application errors
            return@withContext ResultOf.Error(Exception("error"))
        }
      return@withContext ResultOf.Success(launch)
        }

}