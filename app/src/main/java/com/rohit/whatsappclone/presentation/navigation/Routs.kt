package com.rohit.whatsappclone.presentation.navigation

import kotlinx.serialization.Serializable


sealed class AuthRouts {
    @Serializable
    data object Root: AuthRouts()
    @Serializable
    data object SplashScreen: AuthRouts()
    @Serializable
    data object SignUpScreen: AuthRouts()
    @Serializable
    data object SignInScreen: AuthRouts()
}

sealed class DashBoardRouts {
    @Serializable
    data object Root: DashBoardRouts()
    @Serializable
    data object HomeScreen: DashBoardRouts()
    @Serializable
    data object StatusScreen : DashBoardRouts()
}