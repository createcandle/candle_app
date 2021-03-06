package io.badwolf.candleapp

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import io.badwolf.candleapp.utils.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.livedata.observeAsState
import io.badwolf.candleapp.ui.menus.Drawer
import io.badwolf.candleapp.utils.BackPressHandler
import io.badwolf.candleapp.utils.NavigationHost
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(){
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val currentScreen by viewModel.currentScreen.observeAsState()

    if (scaffoldState.drawerState.isOpen){
        BackPressHandler {
            scope.launch{
                scaffoldState.drawerState.close()
            }
        }
    }

    val topBar: @Composable () -> Unit= {
        TopAppBar(
            title= { Text(currentScreen!!.title) },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
                {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
    }

    Scaffold(
        topBar = { topBar() },
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer{ screen ->
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                navController.navigate(screen){
                    launchSingleTop = true
                }
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
    ){ _ ->
        NavigationHost(navController, viewModel = viewModel)
    }
}