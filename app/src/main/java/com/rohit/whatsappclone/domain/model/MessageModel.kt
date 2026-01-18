package com.rohit.whatsappclone.domain.model

data class MessageModel (
    val messageId:String,
    val message:String,
    val senderId:String,
    val receiverId:String,
    val type:String,
    val seen: Boolean,
    val timestamp:Long
)