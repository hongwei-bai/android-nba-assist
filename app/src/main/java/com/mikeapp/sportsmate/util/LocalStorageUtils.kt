package com.mikeapp.sportsmate.util

import android.content.Context
import java.io.IOException
import java.io.InputStream

object LocalStorageUtils {
    @Throws(IOException::class)
    fun getAssetJsonFile(context: Context, filename: String): String {
        val file: InputStream = context.assets.open(filename)
        val formArray = ByteArray(file.available())
        file.read(formArray)
        file.close()
        return String(formArray)
    }
}