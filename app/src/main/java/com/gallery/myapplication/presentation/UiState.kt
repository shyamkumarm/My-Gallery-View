package com.gallery.myapplication.presentation

/*sealed class FolderUiState {
    data class Success(val galleryItem: List<GalleryItem>) : FolderUiState()
    data class Error(val message: String) : FolderUiState()
    data object Loading : FolderUiState()
}

sealed class FileUiState {
    data class Success(val mediaItem: List<MediaItem>) : FileUiState()
    data class Error(val message: String) : FileUiState()
    data object Loading : FileUiState()
}*/

sealed class GalleryUiState<out T> {
    data class Success<T>(val mediaItem: T) : GalleryUiState<T>()
    data class Error(val message: String) : GalleryUiState<Nothing>()
    data object Loading : GalleryUiState<Nothing>()

}