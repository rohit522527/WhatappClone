package com.rohit.whatsappclone.presentation.screens.selectPPScreen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.rohit.whatsappclone.R
import com.rohit.whatsappclone.presentation.navigation.DashBoardRouts
import com.rohit.whatsappclone.ui.theme.appColor
import com.rohit.whatsappclone.utils.FirebaseResult
import com.rohit.whatsappclone.utils.copyUriToFile
import com.rohit.whatsappclone.utils.uploadImageToCloudinary

@Composable
fun SelectPPScreen(navController: NavHostController) {
    var userName by remember{mutableStateOf("")}
    var userSelectedImage by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri : Uri?->
        userSelectedImage=uri
    }
    var showLoading by remember { mutableStateOf(false) }
    val selectPPScreenVM : SelectPPScreenVM = hiltViewModel()
    val updateUserState = selectPPScreenVM.updateUserState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    LaunchedEffect(updateUserState) {
        when(updateUserState){
            FirebaseResult.Default -> {}
            is FirebaseResult.Error -> {
                Toast.makeText(context, updateUserState.message, Toast.LENGTH_SHORT).show()
                showLoading=false
            }
            FirebaseResult.Loading ->{
                showLoading=true
            }
            is FirebaseResult.Success-> {
                showLoading=false
                Toast.makeText(context, updateUserState.data, Toast.LENGTH_SHORT).show()
                navController.navigate(DashBoardRouts.HomeScreen){
                    popUpTo(0)
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {

                Image(
                    painter =
                        if(userSelectedImage!=null){
                            rememberAsyncImagePainter(model = userSelectedImage)
                        }else{
                           painterResource(R.drawable.user)
                        },
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .border(2.dp, color = Color.Black, CircleShape)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        TextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            modifier = Modifier
                .width(300.dp)
                .padding(vertical = 20.dp),
            singleLine = true,
            label = {
                Text("UserName")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
        )
        IconButton(
            onClick = {
                if (userName.isNotEmpty()){
                    if (userSelectedImage!=null){
                        val file = copyUriToFile(context,userSelectedImage!!)
                        Log.d("tag","${file}")
                        uploadImageToCloudinary(
                            file =file,
                            onSuccess = {url->
                                selectPPScreenVM.updateUser(userName,url)
                                Log.d("tag","image upload success")
                            },
                            onError = {
                                Log.d("tag",it)
                            }
                        )
                    }else{
                        selectPPScreenVM.updateUser(userName,"")
                    }
                }
            },
            modifier = Modifier
                .width(300.dp)
                .background(color = appColor, shape = RoundedCornerShape(12.dp)),
            enabled = !showLoading
        ) {
            if(showLoading)
                CircularProgressIndicator()
            else
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}