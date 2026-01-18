package com.rohit.whatsappclone.presentation.screens.chatScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.rohit.whatsappclone.domain.model.UserModel
import com.rohit.whatsappclone.utils.FirebaseResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, username: String, name: String, profilePic: String,uId: String) {
    val chatScreenVM: ChatScreenVM= hiltViewModel()
    val sendMessagesState = chatScreenVM.sendMessageStatus.collectAsStateWithLifecycle().value
    val getMessagesState = chatScreenVM.getMessagesState.collectAsStateWithLifecycle().value
    var messages = remember { mutableStateOf<UserModel?>(null) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        chatScreenVM.getMessages(uId)
    }
    LaunchedEffect(getMessagesState) {
        when(getMessagesState){
            FirebaseResult.Default -> {}
            is FirebaseResult.Error ->{
                Toast.makeText(context, getMessagesState.message, Toast.LENGTH_SHORT).show()
            }
            FirebaseResult.Loading -> {

            }
            is FirebaseResult.Success -> {
                messages.value=getMessagesState.data.toMessageModel()
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(username.ifEmpty { name }) },
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" )
                        }
                        Image(
                            painter = rememberAsyncImagePainter(profilePic),
                            contentDescription = "",
                            modifier=Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                actions = {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                }
            )
        }
    ) {
        Button(
            onClick = {

            }
        ) { }
    }
}