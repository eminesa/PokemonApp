package com.eminesa.pokemonapp.util

import android.content.Context
import androidx.annotation.StringRes
import com.eminesa.pokemonapp.enums.ErrorCode
import retrofit2.HttpException
import java.io.IOException

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}

fun getNetworkErrorMessage(e: Throwable): String {
    return if (e is HttpException) {
        when (e.code()) {

            400 -> {
                ErrorCode.ERRORMESSAGE.name
            }
            401 -> {
                ErrorCode.LOGOUT.name
            }
            404 -> {
                ErrorCode.KILLWARNING.name
            }
            500 -> {
                ErrorCode.KILLWARNING.name
            }
            else -> {
                ErrorCode.ERRORMESSAGE.name
            }
        }
    } else if(e is IOException) {
        ErrorCode.KILLWARNING.name
    } else{
        ErrorCode.ERRORMESSAGE.name
    }
}


