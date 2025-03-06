package com.gallery.myapplication.data

import android.content.Context
import android.provider.MediaStore
import com.gallery.myapplication.domain.MediaItem

class MediaSource(private val application: Context) {


    suspend fun fetchMediaSource(): List<MediaItem> {
        val mediaMap = mutableListOf<MediaItem>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.DATA
        )
        val selection =
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val cursor = application.contentResolver.query(
            MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, null
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val filePath = it.getString(dataColumn)
                val uri = MediaStore.Files.getContentUri("external", id)

                val fileName = filePath.substringAfterLast("/")
                val folderName = filePath.substringBeforeLast("/").substringAfterLast("/")

                val mediaItem = MediaItem(uri, fileName, folderName)

                mediaMap.add(mediaItem)
            }
        }

        return mediaMap

    }
}