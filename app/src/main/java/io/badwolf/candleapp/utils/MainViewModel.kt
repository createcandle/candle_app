package io.badwolf.candleapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import io.badwolf.candleapp.ui.NavRoutes

class MainViewModel: ViewModel() {
    private val _currentScreen = MutableLiveData<NavRoutes>(NavRoutes.DrawerItems.ThingsScreen)
    val currentScreen: LiveData<NavRoutes> = _currentScreen

    fun setCurrentScreen(screen: NavRoutes){
        _currentScreen.value = screen
    }
}