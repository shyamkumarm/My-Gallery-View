package com.gallery.myapplication.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gallery.myapplication.data.MediaRepository
import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MyGalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MediaRepository(application)
    private val _mediaItems =
        MutableStateFlow<List<GalleryItem>>(mutableListOf())
    val mediaItems = _mediaItems.asStateFlow()

    init {
        viewModelScope.launch {

            repository.fetchMedia().flowOn(Dispatchers.IO).collect { folderList ->
                val folderItem =
                    folderList.transformToImageItems() + folderList.transformToVideoItems() + folderList.transformToFolderItems()
                Log.d("MyGalleryViewModel", "mediaItems: $folderItem")
                _mediaItems.emit(folderItem)
            }
        }
    }

    private fun List<MediaItem>.transformToFolderItems(): List<GalleryItem> {
        return this.groupBy { it.folderName }
            .map { (folderName, mediaItems) ->
                GalleryItem(folderName, mediaItems.toMutableList())
            }
    }

    private fun List<MediaItem>.transformToImageItems(): List<GalleryItem> {
        return this.filter {
            it.fileName.endsWith(".jpg", ignoreCase = true) ||
                    it.fileName.endsWith(".jpeg", ignoreCase = true) ||
                    it.fileName.endsWith(".png", ignoreCase = true) ||
                    it.fileName.endsWith(".gif", ignoreCase = true)
        } // Add other image extensions as needed
            .groupBy { "All Images" }
            .map { (folderName, mediaItems) ->
                GalleryItem(folderName, mediaItems.toMutableList())
            }
    }

    private fun List<MediaItem>.transformToVideoItems(): List<GalleryItem> {
        return this.filter {
            it.fileName.endsWith(
                ".mp4",
                ignoreCase = true
            ) || it.fileName.endsWith(".avi", ignoreCase = true) || it.fileName.endsWith(
                ".mov",
                ignoreCase = true
            )
        } // Add other video extensions as needed.
            .groupBy { "All Videos" }
            .map { (folderName, mediaItems) ->
                GalleryItem(folderName, mediaItems.toMutableList())
            }
    }
}
