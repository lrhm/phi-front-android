package xyz.lrhm.phiapp.ui.loginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.EntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.lrhm.phiapp.core.data.model.ResultOf
import xyz.lrhm.phiapp.core.data.model.succeeded
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.core.util.CacheUtil
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val appRepository: AppRepository, val cacheUtil: CacheUtil) : ViewModel() {


    fun login(username:String, password:String){

        viewModelScope.launch {

            val cachedUser =  cacheUtil.getCachedUser()

            Timber.d("cached user before calling is ${cachedUser}")

          val res=  appRepository.doLogin(username, password)

            Timber.d( "login is ${res}")
            if( res is ResultOf.Success)
            {
                Timber.d( "login is ${res.data}")

                val user = appRepository.remoteDataSource.getUser()
            }
        }
    }
}