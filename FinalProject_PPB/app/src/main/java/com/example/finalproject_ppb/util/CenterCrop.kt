package com.example.finalproject_ppb.util

import android.graphics.Bitmap

fun Bitmap.centerCrop(): Bitmap{
    val xStart = 920
    val yStart = 1030
    val squareSize = 1230

    return Bitmap.createBitmap(this, xStart, yStart, squareSize, squareSize)
}