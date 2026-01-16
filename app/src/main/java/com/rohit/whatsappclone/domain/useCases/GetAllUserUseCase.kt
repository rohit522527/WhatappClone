package com.rohit.whatsappclone.domain.useCases

import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.domain.model.UserModel
import com.rohit.whatsappclone.domain.reposetory.Repo
import com.rohit.whatsappclone.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUserUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend operator  fun invoke(): Flow<FirebaseResult<List<UserDTO>>>{
        return  repo.getAllUsers()
    }
}