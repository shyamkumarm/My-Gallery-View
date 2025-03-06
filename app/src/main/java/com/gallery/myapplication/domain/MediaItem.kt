package com.gallery.myapplication.domain

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class MediaItem(
    val uri: Uri,
    val fileName: String,
    val folderName: String
)