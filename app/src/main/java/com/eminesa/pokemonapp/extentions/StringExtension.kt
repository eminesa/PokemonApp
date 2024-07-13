package com.eminesa.pokemonapp.extentions


import android.content.Context
import android.widget.Toast
import com.eminesa.pokemonapp.R
import com.eminesa.pokemonapp.enums.ErrorCode

fun String.showNetworkError(context: Context, isErrorShouldAppear: Boolean = true) {
    when (this) {
        ErrorCode.KILLWARNING.name -> {
            context.resources.getString(R.string.could_not_reach_server_error).showToastMessage(context)
        }
        ErrorCode.LOGOUT.name -> {
            context.resources.getString(R.string.please_try_again).showToastMessage(context)
        }
        else -> {
            if (isErrorShouldAppear)
                context.resources.getString(R.string.please_try_again).showToastMessage(context)
        }
    }
}

fun String.showToastMessage(mContext: Context) {
    Toast.makeText(mContext, this, Toast.LENGTH_SHORT).show()
}