package com.contactrapide.app

import android.content.Context

class FavoritesManager(private val context: Context) {

    private val preferences =
        context.getSharedPreferences(
            "contactrapide_favoris",
            Context.MODE_PRIVATE
        )

    fun ajouter(numero: String) {
        preferences.edit()
            .putBoolean(numero, true)
            .apply()
    }

    fun supprimer(numero: String) {
        preferences.edit()
            .remove(numero)
            .apply()
    }

    fun estFavori(numero: String): Boolean {
        return preferences.getBoolean(numero, false)
    }

    fun obtenirFavoris(): Set<String> {
        return preferences.all.keys
    }
}
