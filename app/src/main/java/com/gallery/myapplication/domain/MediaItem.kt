package com.gallery.myapplication.domain

import android.net.Uri

data class MediaItem(
    val uri: Uri,
    val fileName: String,
    val folderName: String
)