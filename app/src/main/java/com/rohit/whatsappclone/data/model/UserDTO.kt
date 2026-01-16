package com.rohit.whatsappclone.data.model

data class UserDTO(
    val uId: String="",
    val name:String="",
    val email:String="",
    val password:String="",
    val profilePic:String="",
    val userName:String="",
    val createdAt:Long=0L,
    val dateOfBirth: Long=0L
)
