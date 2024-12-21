package com.example.jumatexpress

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object LogbookList : Screen("logbook_list")
    object LogbookEntry : Screen("logbook_entry")
    object LogbookDetail : Screen("logbook_detail/{entryId}") {
        fun createRoute(entryId: String) = "logbook_detail/$entryId"
        const val routeWithArgs = "logbook_detail/{entryId}"
    }
}

@Composable
fun LogbookNavigation(
    navController: NavHostController = rememberNavController()
) {
    // Mendapatkan instance MainViewModel menggunakan viewModel()
    val mainViewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.LogbookList.route
    ) {
        composable(route = Screen.LogbookList.route) {
            // Menyediakan mainViewModel untuk LogbookScreenWrapper
            LogbookScreenWrapper(
                mainViewModel = mainViewModel, // Menyertakan mainViewModel di sini
                navController = navController,
                onItemClick = { entryId ->
                    navController.navigate(Screen.LogbookDetail.createRoute(entryId.toString()))
                }
            )
        }
    }
}

