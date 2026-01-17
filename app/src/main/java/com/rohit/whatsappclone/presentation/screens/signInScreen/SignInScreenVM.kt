package com.rohit.whatsappclone.presentation.screens.signInScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohit.whatsappclone.domain.useCases.LogInUserUseCse
import com.rohit.whatsappclone.utils.FirebaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInScreenVM @Inject constructor(
    private val logInUserUseCse: LogInUserUseCse
): ViewModel() {
    private var _signInStatus = MutableStateFlow<FirebaseResult<String>>(FirebaseResult.Default)
    val signInStatus : StateFlow<FirebaseResult<String>> = _signInStatus

    fun signIn(email:String, password:String){
        viewModelScope.launch {
            logInUserUseCse(email,password).collect {result ->
                _signInStatus.value=result
            }
        }
    }
}