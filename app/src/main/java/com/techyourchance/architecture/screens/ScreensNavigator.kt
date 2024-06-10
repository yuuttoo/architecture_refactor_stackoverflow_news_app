package com.techyourchance.architecture.screens

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow

class ScreensNavigator {

    private lateinit var parentNavController: NavHostController
    private lateinit var nestedNavController: NavHostController

    val currentBottomTab = MutableStateFlow<BottomTab>(BottomTab.Main)
    val currentRoute = MutableStateFlow<Route?>(null)
    val isRootRoute = MutableStateFlow(false)
    val arguments = MutableStateFlow<Bundle?>(null)//for backstackEntryState但不想暴露，又必須給id,title

    fun navigateBack() {
        if (!nestedNavController.popBackStack()) {
            parentNavController.popBackStack()
        }
    }

    fun toTab(bottomTab: BottomTab) {
        val route = when(bottomTab) {
            BottomTab.Favorites -> Route.FavoritesTab
            BottomTab.Main -> Route.MainTab
        }
        parentNavController.navigate(route.routeName) {
            parentNavController.graph.startDestinationRoute?.let { startRoute ->
                popUpTo(startRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun setParentNavController(parentNavController: NavController) {

    }
    fun setNestedNavController(mainNestedNavController: NavController) {

    }
    companion object {
        val BOTTOM_TABS = listOf(BottomTab.Main, BottomTab.Favorites)
    }

}