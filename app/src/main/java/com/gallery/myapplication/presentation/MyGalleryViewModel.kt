package com.gallery.myapplication.presentation

import android.app.Application
import androidx.collection.ArrayMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gallery.myapplication.data.MediaRepository
import com.gallery.myapplication.domain.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MyGalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MediaRepository(application)
    private val _mediaItems =
        MutableStateFlow<ArrayMap<String, MutableList<MediaItem>>>(ArrayMap<String, MutableList<MediaItem>>())
    val mediaItems = _mediaItems.asStateFlow()

    init {
        viewModelScope.launch {
            repository.fetchMedia().flowOn(Dispatchers.IO).collect { mediaList ->
                _mediaItems.emit(mediaList)
            }
        }
    }
}
