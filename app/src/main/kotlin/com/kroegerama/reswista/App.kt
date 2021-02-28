package com.kroegerama.reswista

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.kroegerama.reswista.controller.Initializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var initializer: Initializer

    override fun onCreate() {
        super.onCreate()
        initializer.init()
    }

    companion object {
        val isForeground get() = ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
    }
}
