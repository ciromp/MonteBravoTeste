package com.ciro.montebravotest

import android.app.Application
import android.content.Context
import com.ciro.montebravotest.network.services
import com.ciro.montebravotest.network.servicesChart

private const val KEY_PREF = "pref"
private const val KEY_TOKEN = "token"

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val preferences by lazy {
            instance.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
        }

        fun saveToken (token : String) {
                preferences.edit()
                    .putString(KEY_TOKEN, token)
                    .apply()
        }

        fun getToken () : String? = preferences.getString(KEY_TOKEN, null)

        val apiServices by lazy {
            services()
        }

        val apiServiceChart by lazy {
            servicesChart()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}