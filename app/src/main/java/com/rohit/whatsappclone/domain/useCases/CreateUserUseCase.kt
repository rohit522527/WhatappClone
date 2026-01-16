package com.rohit.whatsappclone.domain.useCases

import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.domain.reposetory.Repo
import com.rohit.whatsappclone.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repo: Repo
){
    suspend operator fun invoke(usrDTO: UserDTO): Flow<FirebaseResult<String>>{
        return repo.createUser(usrDTO)
    }
}