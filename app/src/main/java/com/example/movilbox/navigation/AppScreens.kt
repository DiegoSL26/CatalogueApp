package com.example.movilbox.navigation

sealed class AppScreens(val route: String) {
    object CatalogueScreen: AppScreens("catalogue_screen")
    object ProductScreen: AppScreens("product_screen")
}