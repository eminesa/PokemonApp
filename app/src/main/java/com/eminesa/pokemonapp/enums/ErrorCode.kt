package com.eminesa.pokemonapp.enums

enum class ErrorCode {

    /**
     * Servise erişimin olmaması ve sayfanın bulunamaması durumu
     */
    KILLWARNING,

    /**
     * Servise hatalı istek atılması durumu
     */
    ERRORMESSAGE,

    /**
     * Servise istekte bulunan kullanıcının unauthorized olması (silinmesi vs) durumu
     */
    LOGOUT
}