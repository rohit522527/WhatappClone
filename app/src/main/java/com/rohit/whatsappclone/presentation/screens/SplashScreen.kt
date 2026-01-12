package com.rohit.whatsappclone.presentation.screens

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.rohit.whatsappclone.R
import com.rohit.whatsappclone.presentation.navigation.AuthRouts
import com.rohit.whatsappclone.presentation.navigation.DashBoardRouts

@Composable
fun SplashScreen(navController: NavController) {
    val ofSetX = remember { Animatable(-300f) }
    val size = remember { Animatable(200f) }
    LaunchedEffect(Unit) {
        ofSetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        size.animateTo(
            targetValue = 2000f,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            )
        )
        navController.navigate(
            if (FirebaseAuth.getInstance().currentUser != null) DashBoardRouts.Root else AuthRouts.SignUpScreen
        ) {
            popUpTo(0)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.chatapp),
            contentDescription = "AppImage",
            modifier = Modifier
                .size(size.value.dp)
                .offset(x = ofSetX.value.dp)
        )
    }
}