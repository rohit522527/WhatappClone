package com.rohit.whatsappclone.presentation.screens.chatScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohit.whatsappclone.data.model.MessageDTO
import com.rohit.whatsappclone.domain.useCases.GetMessagesUseCase
import com.rohit.whatsappclone.domain.useCases.SendMessageUseCase
import com.rohit.whatsappclone.utils.FirebaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenVM @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase: GetMessagesUseCase
) : ViewModel() {
    private var _sendMessageStatus =
        MutableStateFlow<FirebaseResult<String>>(FirebaseResult.Default)
    val sendMessageStatus: StateFlow<FirebaseResult<String>> = _sendMessageStatus

    private var _getMessagesState =
        MutableStateFlow<FirebaseResult<List<MessageDTO>>>(FirebaseResult.Default)
    val getMessagesState: StateFlow<FirebaseResult<List<MessageDTO>>> = _getMessagesState

    fun getMessages(receiverId: String) {
        viewModelScope.launch {
            getMessageUseCase(receiverId).collect { result ->
                _getMessagesState.value=result
            }
        }
    }
    fun sendMessage(messageDTO: MessageDTO,receiverId: String){
        viewModelScope.launch {
            sendMessageUseCase(messageDTO,receiverId).collect {result ->
                _sendMessageStatus.value=result
            }
        }
    }

}