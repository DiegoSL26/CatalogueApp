package com.example.movilbox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movilbox.main.MainViewModel
import com.example.movilbox.screens.MainScreen
import com.example.movilbox.screens.ProductScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.CatalogueScreen.route) {
        composable(route = AppScreens.CatalogueScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = AppScreens.ProductScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AppScreens.CatalogueScreen.route)
            }
            val parentViewModel = hiltViewModel<MainViewModel>(parentEntry)
            ProductScreen(viewModel = parentViewModel, navController = navController)
        }
    }
}