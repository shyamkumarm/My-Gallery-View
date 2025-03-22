package com.gallery.myapplication.domain

import com.gallery.myapplication.presentation.GalleryUiState
import com.gallery.myapplication.utils.TransformUtils.transformToAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MediaUseCase(private val mediaRepo: IMediaFile) {
    fun getFolderItems() = flow {
        val mediaItem = mediaRepo.fetchMedia().fold(
            onSuccess = { mediaItem ->
                GalleryUiState.Success(mediaItem = mediaItem.transformToAll())
            }, onFailure = { exception ->
                GalleryUiState.Error(exception.message ?: "Some thing wrong with media cursor")
            })
        emit(mediaItem)
    }.flowOn(Dispatchers.IO)


    fun getFileItems(galleryItems: List<GalleryItem>, folderId: String) = flow {
        val filesItem = galleryItems.filter { it.folderId == folderId }
        emit(filesItem.flatMap { it.fileList })
    }.flowOn(Dispatchers.IO)

}