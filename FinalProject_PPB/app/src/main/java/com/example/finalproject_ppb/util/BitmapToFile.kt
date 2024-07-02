package com.example.finalproject_ppb.util

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun bitmapToFile(bitmap: Bitmap, context: Context): File{
    val currentTime = SimpleDateFormat("yyyy-MM-dd HH_mm_ss", Locale.US).format(Date())
    val imageFile = File(context.filesDir, "$currentTime.jpg")

    val fos = FileOutputStream(imageFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)

    return imageFile
}