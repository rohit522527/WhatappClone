package com.rohit.whatsappclone.utils

sealed class FirebaseResult <out T> {
    object Loading : FirebaseResult<Nothing>()
    object Default : FirebaseResult<Nothing>()
    data class Success<out T>(val data:T): FirebaseResult<T>()
    data class Error(val message: String) : FirebaseResult<Nothing>()
}