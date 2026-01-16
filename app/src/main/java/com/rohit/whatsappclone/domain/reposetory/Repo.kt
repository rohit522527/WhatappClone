package com.rohit.whatsappclone.domain.reposetory

import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.utils.FirebaseResult
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun createUser(userDTO: UserDTO): Flow<FirebaseResult<String>>
    suspend fun updateUser(userName: String,profilePic: String):Flow<FirebaseResult<String>>
    suspend fun getAllUsers():Flow<FirebaseResult<List<UserDTO>>>
    suspend fun logInUser(email:String,password: String): Flow<FirebaseResult<String>>
}