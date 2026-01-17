package com.rohit.whatsappclone.presentation.screens.selectPPScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohit.whatsappclone.domain.useCases.UpdateUserUseCase
import com.rohit.whatsappclone.utils.FirebaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SelectPPScreenVM @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {
    private var _updateUserState= MutableStateFlow<FirebaseResult<String>>(FirebaseResult.Default)
    val updateUserState : StateFlow<FirebaseResult<String>> = _updateUserState

    fun updateUser(userName:String,profilePic: String){
        viewModelScope.launch {
            updateUserUseCase(userName,profilePic).collect { result ->
                _updateUserState.value=result
            }
        }
    }
}