package com.rohit.whatsappclone.data.mapper

import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.domain.model.UserModel

fun List<UserDTO>.toUserModel():List<UserModel>{
    return this.map {it->
        UserModel(
            uId = it.uId,
            name = it.name,
            email = it.email,
            profilePic = it.profilePic,
            dateOfBirth = it.dateOfBirth,
            userName = it.userName
        )
    }
}