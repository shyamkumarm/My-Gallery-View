package com.gallery.myapplication.domain

data class GalleryItem(
    val folderId: String,
    val folderName: String,
    val fileList: MutableList<MediaItem>,

)
