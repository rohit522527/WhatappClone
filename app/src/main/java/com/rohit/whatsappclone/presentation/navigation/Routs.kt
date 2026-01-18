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
    @Serializable
    data object SelectPPScreen: AuthRouts()
}

sealed class DashBoardRouts {
    @Serializable
    data object Root: DashBoardRouts()
    @Serializable
    data object HomeScreen: DashBoardRouts()
    @Serializable
    data object StatusScreen : DashBoardRouts()
    @Serializable
    data object CallScreen : DashBoardRouts()
    @Serializable
    data class ChatScreen(val name:String,val userName: String,val profilePic:String,val uId: String) : DashBoardRouts()
}
@Serializable
sealed class BottomNavigationRouts(val rout:String){
    @Serializable
    object DashBoardScreen: BottomNavigationRouts("Dashboard")
    @Serializable
    object StatusScreen: BottomNavigationRouts("Status")
    @Serializable
    object CallScreen: BottomNavigationRouts("Call")
}