package com.kroegerama.reswista.ui

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.kroegerama.kaiteki.baseui.ViewBindingActivity
import com.kroegerama.kaiteki.onClick
import com.kroegerama.reswista.R
import com.kroegerama.reswista.controller.Persistence
import com.kroegerama.reswista.databinding.AcMainBinding
import com.kroegerama.reswista.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AcMain : ViewBindingActivity<AcMainBinding>(AcMainBinding::inflate) {

    @Inject
    lateinit var prefs: Persistence

    private val viewModel by viewModels<MainViewModel>()

    override fun prepare() {
        setTheme(R.style.AppTheme)
    }

    override fun AcMainBinding.setupGUI() {
        val navController = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)

        btnSwitchFrag.onClick {
            navController.navigate(R.id.fragStart)//, null, navOptions { popUpTo(R.id.main_nav) { inclusive = true } })
        }
    }
}
