package xyz.lrhm.phiapp.core.data.source.remoteDataSource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.lrhm.LoginQuery
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val apolloClient: ApolloClient
) {

  suspend  fun login(username:String, password:String) = withContext(Dispatchers.IO) {
        val response = try {
            apolloClient.query(LoginQuery("","")).await()
        } catch (e: ApolloException) {
            // handle protocol errors
            return@withContext null
        }

        val launch = response.data?.tokenPayload
        if (launch == null || response.hasErrors()) {
            // handle application errors
            return@withContext null
        }
      return@withContext launch
        }

}