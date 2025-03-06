package com.gallery.myapplication.domain

import com.gallery.myapplication.utils.TransformUtils.transformToFolderItems
import com.gallery.myapplication.utils.TransformUtils.transformToImageItems
import com.gallery.myapplication.utils.TransformUtils.transformToVideoItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MediaUseCase(private val mediaRepo: IMediaFile) {
    fun getFolderItems() = flow {
        val folderItem = mediaRepo.fetchMedia().run {
            transformToImageItems() + transformToVideoItems() + transformToFolderItems()
        }
        emit(folderItem)
    }.flowOn(Dispatchers.IO)


    fun getFileItems(galleryItems: List<GalleryItem>, folderId: String) = flow {
        val filesItem = galleryItems.filter { it.folderId == folderId }
        emit(filesItem.flatMap { it.fileList })
    }.flowOn(Dispatchers.IO)

}