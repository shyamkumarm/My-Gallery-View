package com.gallery.myapplication.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import com.gallery.myapplication.domain.MediaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyGalleryViewModel(private val useCase: MediaUseCase) : ViewModel() {

    private val _mediaItems =
        MutableStateFlow<List<GalleryItem>>(mutableListOf())
    val mediaItems = _mediaItems.asStateFlow()


    private val _fileItems =
        MutableStateFlow<List<MediaItem>>(mutableListOf())
    val fileItems = _fileItems.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.getFolderItems().collect { folderItem ->
                Log.d("MyGalleryViewModel", "mediaItems: $folderItem")
                _mediaItems.emit(folderItem)
            }
        }
    }

    fun getFileItems(folderId: String) {
        viewModelScope.launch {
            useCase.getFileItems(mediaItems.value,folderId).collect{ fileItems ->
                Log.d("MyGalleryViewModel", "fileItems: ${fileItems.size}")
                _fileItems.emit(fileItems)
            }

        }
    }

}
