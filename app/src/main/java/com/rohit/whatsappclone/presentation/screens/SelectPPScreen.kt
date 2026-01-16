package com.rohit.whatsappclone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rohit.whatsappclone.R

@Composable
fun SelectPPScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier.size(200.dp),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(R.drawable.chatapp),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
}