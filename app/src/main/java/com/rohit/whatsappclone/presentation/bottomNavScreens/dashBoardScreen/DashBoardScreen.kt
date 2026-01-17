package com.rohit.whatsappclone.presentation.bottomNavScreens.dashBoardScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rohit.whatsappclone.data.mapper.toUserModel
import com.rohit.whatsappclone.domain.model.UserModel
import com.rohit.whatsappclone.utils.FirebaseResult

@Composable
fun DashBoardScreen(rootNavController: NavController) {
    val dashBoardScreenVM : DashBoardScreenVM= hiltViewModel()
    val getAllUsersState = dashBoardScreenVM.getAllUsersStatus.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var users by  remember { mutableStateOf<List<UserModel>?>(null) }
    
    LaunchedEffect(getAllUsersState) {
        when (getAllUsersState){
            FirebaseResult.Default -> {}
            is FirebaseResult.Error -> {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
            FirebaseResult.Loading -> {

            }
            is FirebaseResult.Success -> {
                users=getAllUsersState.data.toUserModel()
            }
        }
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Text("Hello this is Dashboard Screen${users}")
    }
}