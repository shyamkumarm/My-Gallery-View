package com.gallery.myapplication.domain

import com.gallery.myapplication.FileUiState
import com.gallery.myapplication.FolderUiState
import com.gallery.myapplication.utils.TransformUtils.transformToAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MediaUseCase(private val mediaRepo: IMediaFile) {
    fun getFolderItems() = flow {
        val mediaItem = mediaRepo.fetchMedia().fold(
            onSuccess = { mediaItem ->
                FolderUiState.Success(mediaItem.transformToAll())
            }, onFailure = { exception ->
                FolderUiState.Error(exception.message ?: "Some thing wrong with media cursor")
            })
        emit(mediaItem)
    }.flowOn(Dispatchers.IO)


    fun getFileItems(galleryItems: List<GalleryItem>, folderId: String) = flow {
        val filesItem = galleryItems.filter { it.folderId == folderId }
        emit(FileUiState.Success(filesItem.flatMap { it.fileList }))
    }.flowOn(Dispatchers.IO)

}