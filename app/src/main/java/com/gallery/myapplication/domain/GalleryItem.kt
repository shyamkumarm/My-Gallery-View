package com.gallery.myapplication.domain

import androidx.compose.runtime.Immutable

@Immutable
data class GalleryItem(
    val folderId: String,
    val folderName: String,
    val fileList: List<MediaItem>,

    )
