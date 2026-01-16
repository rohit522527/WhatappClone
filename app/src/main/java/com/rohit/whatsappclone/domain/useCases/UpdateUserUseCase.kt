package com.rohit.whatsappclone.domain.useCases

import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.domain.reposetory.Repo
import com.rohit.whatsappclone.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend operator fun invoke(userName: String,profilePic: String): Flow<FirebaseResult<String>>{
        return repo.updateUser(userName,profilePic)
    }
}