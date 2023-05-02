package com.findandhire.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Patterns
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.regex.Pattern


class Commons {
    companion object {
        private var toast: Toast? = null
        fun showToast(context: Context, msg: String) {
            toast?.cancel()
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            toast?.show()

        }

        fun getMultipartFromUri(context: Context, uri: Uri): MultipartBody.Part {
            val fileDir = context.filesDir
            val file = File(fileDir, "image.png")
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)

            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

            val photoBody: MultipartBody.Part =
                MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestFile!!
                )
            return photoBody
        }

        fun isValidEmail(email: String): Boolean {
            val pattern: Pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }

        fun shareApp(context: Context) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, AppConstants.APP_PLAYSTORE_LINK)
            shareIntent.type = "text/plain"
            context.startActivity(Intent.createChooser(shareIntent,"Share"))
        }

        fun shareSinglePhotoToInstagram(context: Context) {
            val url = "http://test.tribyssapps.com/userImages/6437aa0da02810656008001681369613.png"
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, url)
            shareIntent.type = "imgae/*"
            shareIntent.setPackage("com.instagram.android")
            context.startActivity(Intent.createChooser(shareIntent,"Share"))
        }

    }
}