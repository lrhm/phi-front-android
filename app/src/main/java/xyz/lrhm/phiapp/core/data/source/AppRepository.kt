package xyz.lrhm.phiapp.core.data.source

import xyz.lrhm.phiapp.core.data.source.remoteDataSource.RemoteDataSource
import xyz.lrhm.phiapp.core.util.CacheUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val cacheUtil: CacheUtil
) {

    suspend fun doLogin(username: String, password: String) = remoteDataSource.login(username, password)


    fun isLoggedIn() = cacheUtil.getToken() != ""
}