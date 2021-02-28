package com.kroegerama.reswista.controller

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import com.kroegerama.reswista.BuildConfig
import com.kroegerama.reswista.ImageOkHttp
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Initializer @Inject constructor(
    @ApplicationContext private val context: Context,
    @ImageOkHttp private val imageOkHttp: OkHttpClient
) {

    fun init() {
        initApp()
        initCoil()
    }

    private fun initApp() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initCoil() {
        val imageLoader = ImageLoader.Builder(context).apply {
            if (BuildConfig.DEBUG) {
                logger(DebugLogger())
            }
            okHttpClient(imageOkHttp)

            crossfade(true)
        }.build()
        Coil.setImageLoader(imageLoader)
    }
}
