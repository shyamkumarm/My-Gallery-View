package com.gallery.myapplication.data

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.gallery.myapplication.domain.MediaItem

class MediaSource(private val application: Context) {


    fun fetchMediaSource(): Result<List<MediaItem>> {

        var cursor: Cursor? = null
        try {
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
            val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
            cursor = application.contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
            val mediaMap = mutableListOf<MediaItem>()
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
            return Result.success(mediaMap)
        } catch (e: Exception) {
            return Result.failure(e)
        } finally {
            cursor?.close()
        }

    }
}