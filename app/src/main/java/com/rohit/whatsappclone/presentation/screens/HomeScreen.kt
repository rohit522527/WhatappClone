package com.rohit.whatsappclone.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rohit.whatsappclone.presentation.navigation.AuthRouts
import com.rohit.whatsappclone.presentation.navigation.BottomNavigationRouts
import com.rohit.whatsappclone.presentation.screens.bottomNavScreens.CallScreen
import com.rohit.whatsappclone.presentation.screens.bottomNavScreens.DashBoardScreen
import com.rohit.whatsappclone.presentation.screens.bottomNavScreens.StatusScreen
import com.rohit.whatsappclone.presentation.viewModels.HomeScreenVM
import com.rohit.whatsappclone.utils.BottomNavItem
import com.rohit.whatsappclone.utils.listOfBottomItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(rootNavController: NavController) {
    val subNavController = rememberNavController()
    val currentBackStackEntry by subNavController.currentBackStackEntryAsState()
    val currentRout =currentBackStackEntry?.destination?.route
    var showMenu by remember { mutableStateOf(false) }
    val homeScreenVM : HomeScreenVM= hiltViewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                listOfBottomItems.forEach {item ->
                    NavigationBarItem(
                        selected =currentRout==item.rout,
                        onClick = {
                            subNavController.navigate(item.rout){
                                popUpTo(subNavController.graph.startDestinationId){
                                    saveState=true
                                }
                                launchSingleTop=true
                                restoreState=true
                            }
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = "")
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {Text("Chat App")},
                actions = {
                    IconButton(
                        onClick = {
                            showMenu=true
                        }
                    ) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = {showMenu=false}
                    ) {
                        DropdownMenuItem(
                            text = {Text("Log out")},
                            onClick = {
                                homeScreenVM.firebaseAuth.signOut()
                                rootNavController.navigate(AuthRouts.SignInScreen){
                                    popUpTo(0)
                                }
                            },
                            leadingIcon = {Icon(imageVector = Icons.Default.Close, contentDescription = "")}
                        )
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = subNavController,
            startDestination = BottomNavigationRouts.DashBoardScreen.rout,
            modifier = Modifier.padding(it)
        ) {
            composable(BottomNavigationRouts.DashBoardScreen.rout) {
                DashBoardScreen(rootNavController = rootNavController)
            }
            composable(BottomNavigationRouts.StatusScreen.rout) {
                StatusScreen(rootNavController = rootNavController)
            }
            composable(BottomNavigationRouts.CallScreen.rout) {
                CallScreen(rootNavController = rootNavController)
            }
        }
    }

}

