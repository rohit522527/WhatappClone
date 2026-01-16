package com.rohit.whatsappclone.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.rohit.whatsappclone.presentation.screens.HomeScreen
import com.rohit.whatsappclone.presentation.screens.SelectPPScreen
import com.rohit.whatsappclone.presentation.screens.SignInScreen
import com.rohit.whatsappclone.presentation.screens.SignUpScreen
import com.rohit.whatsappclone.presentation.screens.SplashScreen
import com.rohit.whatsappclone.presentation.screens.StatusScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthRouts.Root) {
        navigation<AuthRouts.Root>(startDestination = AuthRouts.SplashScreen){
            composable<AuthRouts.SplashScreen>{
                SplashScreen(navController=navController)
            }
            composable<AuthRouts.SignInScreen>{
                SignInScreen(navController=navController)
            }
            composable<AuthRouts.SignUpScreen>{
                SignUpScreen(navController=navController)
            }
            composable<AuthRouts.SelectPPScreen>{
                SelectPPScreen(navController=navController)
            }
        }
        navigation<DashBoardRouts.Root>(startDestination = DashBoardRouts.HomeScreen){
            composable<DashBoardRouts.HomeScreen>{
                HomeScreen(rootNavController=navController)
            }
            composable<DashBoardRouts.StatusScreen>{
                StatusScreen(navController=navController)
            }
        }
    }
}