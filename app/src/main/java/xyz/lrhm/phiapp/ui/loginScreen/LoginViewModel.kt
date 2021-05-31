package xyz.lrhm.phiapp.ui.loginScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.EntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.lrhm.LoginQuery
import xyz.lrhm.phiapp.core.data.model.ResultOf
import xyz.lrhm.phiapp.core.data.model.succeeded
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val appRepository: AppRepository) : ViewModel() {

    val loginResult = MutableLiveData<ResultOf<LoginQuery.TokenPayload>>()
    val loggedIn = MutableLiveData<Boolean>()

    fun login(username: String, password: String) {

        viewModelScope.launch {
            val res = appRepository.doLogin(username, password)

            loginResult.postValue(res)

            Timber.d("login is ${res}")
            if (res is ResultOf.Success) {

                Timber.d("login is ${res.data}")

                val user = appRepository.remoteDataSource.getUser()
                loggedIn.postValue(true)
            }
        }
    }
}