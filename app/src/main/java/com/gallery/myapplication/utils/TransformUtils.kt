package com.gallery.myapplication.utils

import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import java.util.UUID

object TransformUtils {

    fun List<MediaItem>.transformToAll(): List<GalleryItem> {
        return this.transformToImageItems() + this.transformToVideoItems() + this.transformToFolderItems()
    }

    private fun List<MediaItem>.transformToFolderItems(): List<GalleryItem> {
        return this.groupBy { it.folderPath }
            .map { (folderId, mediaItems) ->
                GalleryItem(UUID.randomUUID().toString(), mediaItems[0].folderName, mediaItems.toMutableList())
            }
    }

    private fun List<MediaItem>.transformToImageItems(): List<GalleryItem> {
        return this.filter {
            it.fileName.isImageFile()
        }.groupBy { "All Images" }
            .map { (folderName, mediaItems) ->
                GalleryItem(UUID.randomUUID().toString(), folderName, mediaItems.toMutableList())
            }
    }

    private fun List<MediaItem>.transformToVideoItems(): List<GalleryItem> {
        return this.filter {
            it.fileName.isVideoFile()
        } // Add other video extensions as needed.
            .groupBy { "All Videos" }
            .map { (folderName, mediaItems) ->
                GalleryItem(UUID.randomUUID().toString(), folderName, mediaItems.toMutableList())
            }
    }


    fun String.isImageFile() =
        this.endsWith(".jpg", ignoreCase = true) ||
                this.endsWith(".jpeg", ignoreCase = true) ||
                this.endsWith(".png", ignoreCase = true)
    // Add other image extensions as needed


    fun String.isVideoFile() =
        this.endsWith(
            ".mp4",
            ignoreCase = true
        ) || this.endsWith(".avi", ignoreCase = true) || this.endsWith(
            ".mov",
            ignoreCase = true
        )// Add other video extensions as needed

}
