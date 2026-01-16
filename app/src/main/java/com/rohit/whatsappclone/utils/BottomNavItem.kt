package com.rohit.whatsappclone.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rohit.whatsappclone.presentation.navigation.BottomNavigationRouts

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val rout: String
)
val listOfBottomItems = listOf<BottomNavItem>(
    BottomNavItem(
        label = "Dashboard",
        icon = Icons.Default.Home,
        rout = BottomNavigationRouts.DashBoardScreen.rout
    ) ,BottomNavItem(
        label = "Status",
        icon = Icons.Default.Settings,
        rout = BottomNavigationRouts.StatusScreen.rout
    ) , BottomNavItem(
        label = "Call",
        icon = Icons.Default.Call,
        rout = BottomNavigationRouts.CallScreen.rout
    )
)
