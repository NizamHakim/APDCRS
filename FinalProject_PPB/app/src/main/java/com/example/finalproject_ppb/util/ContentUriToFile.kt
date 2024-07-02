package com.example.finalproject_ppb.util

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

fun contentUriToFile(context: Context, contentUri: Uri): File {
    val contentBytes = context.contentResolver.openInputStream(contentUri)?.use {
        it.readBytes()
    }

    val internalFile = File(context.filesDir, contentUri.lastPathSegment!! + ".jpg")
    FileOutputStream(internalFile).use {
        it.write(contentBytes)
    }

    return internalFile
}