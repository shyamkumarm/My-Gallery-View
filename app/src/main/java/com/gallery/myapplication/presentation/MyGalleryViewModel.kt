package com.gallery.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallery.myapplication.domain.MediaItem
import com.gallery.myapplication.domain.MediaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyGalleryViewModel(private val useCase: MediaUseCase) : ViewModel() {


    val mediaItems = flow {
        useCase.getFolderItems().collect { mediaState ->
            emit(mediaState)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GalleryUiState.Loading)


    private val _fileItems =
        MutableStateFlow<GalleryUiState<List<MediaItem>>>(GalleryUiState.Loading)
    val fileItems = _fileItems.asStateFlow()


    fun getFileItems(folderId: String) {
        viewModelScope.launch {
            _fileItems.emit(GalleryUiState.Loading)
            try {
                useCase.getFileItems(
                    (mediaItems.value as GalleryUiState.Success).mediaItem,
                    folderId
                ).collect { fileItems ->
                    _fileItems.emit(GalleryUiState.Success(fileItems))
                }
            } catch (e: Exception) {
                _fileItems.emit(GalleryUiState.Error(e.message ?: "Some thing wrong or No Data"))
            }

        }
    }

}
