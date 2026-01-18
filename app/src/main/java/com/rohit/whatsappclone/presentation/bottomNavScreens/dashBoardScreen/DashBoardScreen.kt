package com.rohit.whatsappclone.presentation.bottomNavScreens.dashBoardScreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.rohit.whatsappclone.R
import com.rohit.whatsappclone.data.mapper.toUserModel
import com.rohit.whatsappclone.domain.model.UserModel
import com.rohit.whatsappclone.presentation.navigation.BottomNavigationRouts
import com.rohit.whatsappclone.presentation.navigation.DashBoardRouts
import com.rohit.whatsappclone.utils.FirebaseResult
import java.io.File

@Composable
fun DashBoardScreen(rootNavController: NavController) {
    val dashBoardScreenVM: DashBoardScreenVM = hiltViewModel()
    val getAllUsersState = dashBoardScreenVM.getAllUsersStatus.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var users by remember { mutableStateOf<List<UserModel>?>(null) }

    LaunchedEffect(getAllUsersState) {
        when (getAllUsersState) {
            FirebaseResult.Default -> {}
            is FirebaseResult.Error -> {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            FirebaseResult.Loading -> {

            }

            is FirebaseResult.Success -> {
                users = getAllUsersState.data.toUserModel()
            }
        }
    }
    users?.let { userList ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(userList) { user ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable{
                            rootNavController.navigate(DashBoardRouts.ChatScreen(
                                name = user.name,
                                userName = user.userName,
                                profilePic = user.profilePic,
                                uId=user.uId
                            )){
                                popUpTo(DashBoardRouts.HomeScreen)
                            }
                        }
                ) {
                    Image(
                        painter = if (user.profilePic.isNotEmpty()) {
                            rememberAsyncImagePainter(user.profilePic)
                        } else {
                            painterResource(R.drawable.user)
                        },
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(user.userName.ifEmpty { user.name }, fontSize = 24.sp)
                }
            }
        }
    }
}