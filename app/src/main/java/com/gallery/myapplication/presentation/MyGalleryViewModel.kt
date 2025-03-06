package com.gallery.myapplication.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallery.myapplication.FileUiState
import com.gallery.myapplication.FolderUiState
import com.gallery.myapplication.domain.MediaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyGalleryViewModel(private val useCase: MediaUseCase) : ViewModel() {

    private val _mediaItems =
        MutableStateFlow<FolderUiState>(FolderUiState.Loading)
    val mediaItems = _mediaItems.asStateFlow()


    private val _fileItems =
        MutableStateFlow<FileUiState>(FileUiState.Loading)
    val fileItems = _fileItems.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.getFolderItems().collect { mediaState ->
                Log.d("MyGalleryViewModel", "mediaItems: ${(mediaState as FolderUiState.Success).galleryItem.map { 
                    "${it.folderId} -> ${it.folderName}"
                }}")
                _mediaItems.emit(mediaState)
            }
        }
    }

    fun getFileItems(folderId: String) {
        viewModelScope.launch {
            _fileItems.emit(FileUiState.Loading)
            try {
                useCase.getFileItems(
                    (mediaItems.value as FolderUiState.Success).galleryItem,
                    folderId
                ).collect { fileItems ->
                    Log.d("MyGalleryViewModel", "$folderId -> fileItems: ${fileItems.mediaItem}")
                    _fileItems.emit(fileItems)
                }
            } catch (e: Exception) {
                _fileItems.emit(FileUiState.Error(e.message ?: "Some thing wrong or No Data"))
            }

        }
    }

}
