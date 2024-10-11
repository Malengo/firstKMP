package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.authentication.UI.LoginScreen
import org.example.project.homeApp.HomeScreen
import org.example.project.profile.ProfileScreen
import org.example.project.sharedViewModel.SharedProfileViewModel


enum class AppRouterName {
    Login,
    Home,
    ProfileScreen
}

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val sharedProfileViewModel: SharedProfileViewModel =
            viewModel { SharedProfileViewModel() }
        NavHost(navController = navController, startDestination = AppRouterName.Login.name) {
            composable(AppRouterName.Login.name) {
                LoginScreen(
                    { navController.navigate(AppRouterName.Home.name) },
                    sharedProfileViewModel
                )
            }
            composable(AppRouterName.Home.name) {
                HomeScreen(navController, sharedProfileViewModel)
            }
            composable(AppRouterName.ProfileScreen.name) {
                ProfileScreen(sharedProfileViewModel)
            }
        }
    }
}






