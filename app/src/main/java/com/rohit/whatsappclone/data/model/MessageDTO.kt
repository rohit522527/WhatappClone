package com.rohit.whatsappclone.data.model

data class MessageDTO(
    val messageId:String,
    val message:String="",
    val senderId:String="",
    val receiverId:String="",
    val type:String="text",
    val seen: Boolean=false,
    val timestamp:Long=0L

)
