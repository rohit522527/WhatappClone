package com.rohit.whatsappclone.domain.useCases

import com.rohit.whatsappclone.data.model.MessageDTO
import com.rohit.whatsappclone.domain.reposetory.Repo
import com.rohit.whatsappclone.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend operator fun invoke(messageDTO: MessageDTO,receiverId: String): Flow<FirebaseResult<String>> {
        return repo.sendMessage(messageDTO,receiverId)
    }
}