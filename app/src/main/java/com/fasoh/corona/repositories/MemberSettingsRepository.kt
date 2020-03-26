package com.fasoh.corona.repositories

import android.content.Context
import android.content.SharedPreferences

class SettingsRepository private constructor(
    private val defaultSharedPreferences: SharedPreferences
) {
    var defaultCountry: String?
        get() = defaultSharedPreferences.getString(COUNTRY, "")
        set(defaultCountry) = defaultSharedPreferences.edit().putString(COUNTRY, defaultCountry).apply()

    var defaultDial: String?
        get() = defaultSharedPreferences.getString(COUNTRY_DIAL, "")
        set(defaultCountry) = defaultSharedPreferences.edit().putString(COUNTRY_DIAL, defaultCountry).apply()

    companion object {

        private fun create(context: Context): SettingsRepository =
            SettingsRepository(context.getSharedPreferences("corona-app", 0))

        fun getInstance(context: Context): SettingsRepository {
            if (instance == null) {
                instance = create(context)
            }
            return instance!!
        }

        const val COUNTRY = "USER_REF"
        const val COUNTRY_DIAL ="COUNTRY_DIAL"

        private var instance: SettingsRepository? = null
    }
}