package com.rohit.whatsappclone.utils

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import java.io.File
import java.io.FileOutputStream

fun copyUriToFile(context: Context, uri: Uri): File {
    val file = File(
        context.filesDir,
        "profile_${System.currentTimeMillis()}.jpg"
    )
    context.contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    return file
}

fun uploadImageToCloudinary(
    file: File,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
) {
    MediaManager.get()
        .upload(file.absolutePath)
        .option("folder","profile_pics")
        .option("upload_preset", "profile_unsigned")
        .callback(object : UploadCallback{
            override fun onStart(requestId: String?) {

            }
            override fun onProgress(
                requestId: String?,
                bytes: Long,
                totalBytes: Long
            ) {

            }

            override fun onSuccess(
                requestId: String?,
                resultData: Map<*, *>?
            ) {
               val imageUrl= resultData?.get("secure_url") as String
                onSuccess(imageUrl)
            }

            override fun onError(
                requestId: String?,
                error: ErrorInfo?
            ) {
                onError(error?.description?:"Upload failed")
            }

            override fun onReschedule(
                requestId: String?,
                error: ErrorInfo?
            ) {

            }

        })
        .dispatch()
}