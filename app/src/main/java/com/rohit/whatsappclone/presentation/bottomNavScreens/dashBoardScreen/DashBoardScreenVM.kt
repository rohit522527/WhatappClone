package com.rohit.whatsappclone.presentation.bottomNavScreens.dashBoardScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.domain.useCases.GetAllUserUseCase
import com.rohit.whatsappclone.utils.FirebaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardScreenVM @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val getAllUserUseCase: GetAllUserUseCase
): ViewModel() {

    private var _getAllUsersStatus = MutableStateFlow<FirebaseResult<List<UserDTO>>>(FirebaseResult.Default)
    val getAllUsersStatus: StateFlow<FirebaseResult<List<UserDTO>>> = _getAllUsersStatus

    init {
        getAllUsers()
    }
    fun getAllUsers(){
        viewModelScope.launch {
            getAllUserUseCase().collect {result ->
                _getAllUsersStatus.value=result
            }
        }
    }
}