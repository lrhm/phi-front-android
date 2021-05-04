package xyz.lrhm.phiapp.core.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CacheUtil @Inject constructor(@ApplicationContext context: Context) {


    private var sharedPreferences: SharedPreferences =context.getSharedPreferences("myPrefs" , Context.MODE_PRIVATE)
    // use the shared preferences and editor as you normally would
    private var editor = sharedPreferences.edit()

    fun storeToken(token:String){

        editor.putString(TOKEN_KEY, token).commit()
    }

    fun getToken(): String{


      return  sharedPreferences.getString(TOKEN_KEY, "")!!
    }

    companion object {
        const val TOKEN_KEY = "token"
    }

}