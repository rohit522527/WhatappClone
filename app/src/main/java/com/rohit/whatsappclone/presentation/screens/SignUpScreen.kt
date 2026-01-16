package com.rohit.whatsappclone.presentation.screens

import android.widget.ImageButton
import android.widget.Toast
import androidx.collection.emptyIntSet
import androidx.compose.animation.core.TwoWayConverter
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rohit.whatsappclone.R
import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.presentation.navigation.AuthRouts
import com.rohit.whatsappclone.presentation.viewModels.SignUpScreenVM
import com.rohit.whatsappclone.ui.theme.appColor
import com.rohit.whatsappclone.utils.FirebaseResult
import com.rohit.whatsappclone.utils.MyTextField
import kotlin.math.sign

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var conformPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConformPassword by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }
    val signUpScreenVM: SignUpScreenVM= hiltViewModel()
    val createUserState = signUpScreenVM.createUserState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(createUserState) {
        when (createUserState) {
            is FirebaseResult.Loading -> {
                showLoading=true
            }
            is FirebaseResult.Error -> {
                Toast.makeText(context, createUserState.message, Toast.LENGTH_SHORT).show()
                showLoading=false
            }
            is FirebaseResult.Success -> {
                Toast.makeText(context, createUserState.data, Toast.LENGTH_SHORT).show()
                showLoading=false
                navController.navigate(AuthRouts.SelectPPScreen){
                    popUpTo(0)
                }
            }
            is FirebaseResult.Default -> {}
        }
    }
    Scaffold {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = appColor)
        )
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(.2f)
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    "Create your\nNew Account",
                    fontSize = 34.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W400,
                    lineHeight = 40.sp
                )

            }
            Column(
                modifier = Modifier
                    .weight(.8f)
                    .background(Color.White, shape = RoundedCornerShape(topStart = 50.dp))
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MyTextField(
                    value = name,
                    label = "Enter your name",
                    keyboardCapitalization = KeyboardCapitalization.Words,
                ) { name = it }
                Spacer(Modifier.height(16.dp))
                MyTextField(
                    value = email,
                    label = "Email",
                    keyboardTye = KeyboardType.Email,
                ) { email = it }
                Spacer(Modifier.height(16.dp))
                MyTextField(
                    value = password,
                    label = "Password",
                    keyboardTye = KeyboardType.Password,
                    icon = if (showPassword) R.drawable.eyev else R.drawable.eyeinv,
                    visualtransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    onTrailingIconClick = {
                        showPassword = !showPassword
                    }
                ) { password = it }
                Spacer(Modifier.height(16.dp))
                MyTextField(
                    value = conformPassword,
                    label = "Conform password",
                    keyboardTye = KeyboardType.Password,
                    icon = if (showConformPassword) R.drawable.eyev else R.drawable.eyeinv,
                    visualtransformation = if (showConformPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    onTrailingIconClick = { showConformPassword = !showConformPassword }
                ) { conformPassword = it }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && conformPassword.isNotEmpty() && password==conformPassword){
                            signUpScreenVM.createUser(UserDTO(
                                name = name,
                                email = email,
                                password = password,
                                profilePic = "",
                                dateOfBirth = 0L,
                                userName = "",
                                createdAt = System.currentTimeMillis()
                            ))
                        }
                    },
                    modifier = Modifier.size(width = 300.dp, height = 55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appColor
                    ),
                    enabled = !showLoading
                ) {
                    if (!showLoading)
                    Text("Sign Up")
                    else
                        CircularProgressIndicator()
                }
                Spacer(Modifier.height(10.dp))
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("Already have an account?")
                    }
                    withStyle(style = SpanStyle(color = appColor)) {
                        append("LogIn")
                    }
                }, modifier = Modifier.clickable {
                    navController.navigate(AuthRouts.SignInScreen) {
                        popUpTo(0)
                    }
                })
                Row(
                    modifier = Modifier.width(300.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                val annotatedText=buildAnnotatedString {
                    append("By tapping “Sign Up”, you agree to our\n")
                    pushStringAnnotation("TERMS", "terms")
                    withStyle(style = SpanStyle(color = appColor,textDecoration = TextDecoration.Underline)) {
                        append("Terms of Service ")
                    }
                    pop()
                    append("and")
                    pushStringAnnotation("PRIVACY", "privacy")
                    withStyle(style = SpanStyle(color = appColor,textDecoration = TextDecoration.Underline)) {
                        append(" Privacy Policy")
                    }
                    pop()
                }
                Box(Modifier.weight(1f)) {
                    ClickableText(
                       text=annotatedText,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = it.calculateBottomPadding()),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        ),
                       onClick = {offset->
                           annotatedText.getStringAnnotations("TERMS",offset,offset)
                               .firstOrNull()?.let {

                               }
                           annotatedText.getStringAnnotations("PRIVACY",offset,offset)
                               .firstOrNull()?.let {

                               }
                       }
                    )
                }
            }
        }
    }
}

