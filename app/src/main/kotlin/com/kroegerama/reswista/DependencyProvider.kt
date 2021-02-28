package com.kroegerama.reswista

import android.content.Context
import coil.util.CoilUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyProvider {

    @Provides
    @Singleton
    @ImageOkHttp
    fun provideImageOkHttp(
        @ApplicationContext context: Context
    ) = OkHttpClient.Builder().apply {
        cache(CoilUtils.createDefaultCache(context))
    }.build()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageOkHttp
