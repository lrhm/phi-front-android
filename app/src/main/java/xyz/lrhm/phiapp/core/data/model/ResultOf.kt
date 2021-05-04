package xyz.lrhm.phiapp.core.data.model

sealed class ResultOf<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultOf<T>()
    data class Error(val exception: Exception) : ResultOf<Nothing>()

}


val ResultOf<*>.succeeded: Boolean
    get() = this is ResultOf.Success<*>