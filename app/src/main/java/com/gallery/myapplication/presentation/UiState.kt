package com.gallery.myapplication.presentation

import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem

sealed class FolderUiState {
    data class Success(val galleryItem: List<GalleryItem>) : FolderUiState()
    data class Error(val message: String): FolderUiState()
    data object Loading: FolderUiState()
}

sealed class FileUiState {
    data class Success(val mediaItem: List<MediaItem>) : FileUiState()
    data class Error(val message: String): FileUiState()
    data object Loading: FileUiState()
}