package com.rohit.whatsappclone.domain.model

data class UserModel(
    val uId: String,
    val name:String,
    val email:String,
    val profilePic:String,
    val userName:String,
    val dateOfBirth: Long
)
