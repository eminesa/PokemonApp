package com.eminesa.pokemonapp.extentions

import com.eminesa.pokemonapp.R
import com.eminesa.pokemonapp.model.ErrorDto
import com.eminesa.pokemonapp.model.toErrorModel
import com.eminesa.pokemonapp.util.UiText
import com.google.gson.Gson
import retrofit2.HttpException

val gson = Gson()

@Synchronized
fun HttpException.handleError(): UiText {
    val errorString = this.response()?.errorBody()?.string()
    errorString?.let {
        val errorModel = gson.fromJson(errorString, ErrorDto::class.java)?.toErrorModel()
        if (errorModel?.error != null)
            return UiText.DynamicString(errorModel.error)
        else
            return UiText.StringResource(R.string.unexpectedError)
    }
    return this.localizedMessage?.let { UiText.DynamicString(it) }
        ?: UiText.StringResource(R.string.unexpectedError)
}
