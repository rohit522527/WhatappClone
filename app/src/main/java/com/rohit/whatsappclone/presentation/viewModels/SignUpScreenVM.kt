package com.rohit.whatsappclone.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.domain.useCases.CreateUserUseCase
import com.rohit.whatsappclone.utils.FirebaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenVM @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    private val _createUserState = MutableStateFlow<FirebaseResult<String>>(FirebaseResult.Default)
    val createUserState: StateFlow<FirebaseResult<String>> = _createUserState

    fun createUser(userDTO: UserDTO) {
        viewModelScope.launch {
            createUserUseCase(userDTO).collect { result ->
                _createUserState.value = result
            }
        }
    }
}