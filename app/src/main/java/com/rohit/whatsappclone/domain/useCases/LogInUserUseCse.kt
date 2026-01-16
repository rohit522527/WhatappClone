package com.rohit.whatsappclone.domain.useCases

import com.rohit.whatsappclone.domain.reposetory.Repo
import com.rohit.whatsappclone.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInUserUseCse @Inject constructor(
    private val repo: Repo
){
    suspend operator fun invoke(email:String,password: String): Flow<FirebaseResult<String>>{
        return repo.logInUser(email=email,password=password)
    }
}