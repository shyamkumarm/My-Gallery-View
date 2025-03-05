package com.gallery.myapplication.data

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import kotlinx.coroutines.flow.flow

class MediaRepository(private val application: Application) {


    suspend fun fetchMedia() = flow<List<Pair<Uri, String>>> {
            val mediaList = mutableListOf<Pair<Uri, String>>()
            val projection =
                arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MEDIA_TYPE)
            val selection =
                "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
            val selectionArgs = arrayOf(
                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
            )
            val cursor = MediaStore.Files.getContentUri("external").let {
                application.contentResolver.query(it, projection, selection, selectionArgs, null)
            }
            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val uri = MediaStore.Files.getContentUri("external", id)
                    val fileName = getFileName(application.applicationContext, uri) ?: "Unknown"
                    mediaList.add(uri to fileName)
                }
            }
            cursor?.close()
            emit(mediaList)
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var name: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex)
                }
            }
        }
        cursor?.close()
        return name
    }
