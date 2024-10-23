package org.example.project

import Colors.ColorsDefaults
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.example.project.authentication.ui.LoginScreen
import org.example.project.homeApp.HomeScreen
import org.example.project.profile.ProfileScreen
import org.example.project.sharedViewModel.SharedProfileViewModel


enum class AppRouterName {
    Login,
    Home,
    ProfileScreen
}

@Composable
fun EnglishAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    title: String
) {
    TopAppBar(
        title = { Text(title) },
        modifier = Modifier.padding(top = 35.dp),
        elevation = 0.dp,
        backgroundColor = ColorsDefaults.onPrimaryLight,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = ColorsDefaults.primaryLight
                    )
                }
            }
        },
    )
}

@Composable
fun App(navController: NavHostController = rememberNavController()) {
    val sharedProfileViewModel: SharedProfileViewModel = viewModel { SharedProfileViewModel() }

    NavHost(
        navController = navController,
        startDestination = AppRouterName.Login.name,
        modifier = Modifier.padding()
    ) {
        composable(AppRouterName.Login.name) {
            LoginScreen(
                {
                    navController.navigate(AppRouterName.Home.name)
                },
                sharedProfileViewModel
            )
        }
        composable(AppRouterName.Home.name) {
            HomeScreen(
                { navController.navigate(AppRouterName.ProfileScreen.name) },
                sharedProfileViewModel
            )
        }
        composable(AppRouterName.ProfileScreen.name) {
            Scaffold(
                topBar = {
                    EnglishAppBar(
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        title = "Perfil"
                    )
                }
            ) { innerPadding ->
                ProfileScreen(sharedProfileViewModel, modifier = Modifier.padding(innerPadding))
            }
        }
    }

}






