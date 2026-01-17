package com.rohit.whatsappclone.presentation.screens.signInScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rohit.whatsappclone.R
import com.rohit.whatsappclone.presentation.navigation.AuthRouts
import com.rohit.whatsappclone.presentation.navigation.DashBoardRouts
import com.rohit.whatsappclone.ui.theme.appColor
import com.rohit.whatsappclone.utils.FirebaseResult
import com.rohit.whatsappclone.utils.MyTextField

@Composable
fun SignInScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLoading by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    val signInScreenVM : SignInScreenVM = hiltViewModel()
    val signInStatus = signInScreenVM.signInStatus.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    LaunchedEffect(signInStatus) {
        when(signInStatus){
            FirebaseResult.Default -> {}
            is FirebaseResult.Error -> {
                Toast.makeText(context, signInStatus.message, Toast.LENGTH_SHORT).show()
            showLoading=false
            }
            FirebaseResult.Loading -> {showLoading=true}
            is FirebaseResult.Success<*> -> {
                Toast.makeText(context, "signIn Success", Toast.LENGTH_SHORT).show()
                showLoading=false
                navController.navigate(DashBoardRouts.HomeScreen){
                    popUpTo(0)
                }
            }

        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(color = appColor)
    )
    Column(
        Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .weight(.3f)

        ) {
            Card(modifier = Modifier.size(80.dp), shape = CircleShape) {
                Image(
                    painter = painterResource(R.drawable.chatapp),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                "LogIn to your\nAccount",
                fontSize = 34.sp,
                color = Color.White,
                fontWeight = FontWeight.W400,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .weight(.7f)
                .background(color = Color.White, shape = RoundedCornerShape(topStart = 32.dp))
                .padding(top = 24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTextField(
                value = email,
                label = "email",
                keyboardTye = KeyboardType.Email,
            ) { email = it }
            Spacer(Modifier.height(20.dp))
            MyTextField(
                value = password,
                label = "password",
                keyboardTye = KeyboardType.Password,
                visualtransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                icon = if (showPassword) R.drawable.eyev else R.drawable.eyeinv,
                onTrailingIconClick = {showPassword=!showPassword}
            ) { password = it }
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()){
                        signInScreenVM.signIn(email=email, password = password)
                    }
                },
                modifier = Modifier.size(height = 55.dp, width = 300.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = appColor
                ),
                enabled = !showLoading
            ) {
                if (showLoading)
                    CircularProgressIndicator()
                else
                    Text("Sign In")
            }

            Text(text = buildAnnotatedString {
                append("I don't have any account?")
                withStyle(style = SpanStyle(color = appColor)) {
                    append("Create Account")
                }
            }, modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                    navController.navigate(AuthRouts.SignUpScreen) {
                        popUpTo(AuthRouts.SignInScreen) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                })
            Row(modifier = Modifier.width(300.dp), verticalAlignment = Alignment.CenterVertically) {
                Divider(modifier = Modifier.weight(1f))
                Text("or")
                Divider(modifier = Modifier.weight(1f))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.width(300.dp)
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    Image(
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    Image(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "",
                        modifier = Modifier
                            .rotate(90f)
                            .size(32.dp)
                    )
                }
            }
        }
    }
}