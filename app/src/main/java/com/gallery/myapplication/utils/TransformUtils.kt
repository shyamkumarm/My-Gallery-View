package com.gallery.myapplication.utils

import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import java.util.UUID

object TransformUtils {

    fun List<MediaItem>.transformToAll(): List<GalleryItem> {
        return this.transformToFolderItems()
    }

    private fun List<MediaItem>.transformToFolderItems(): List<GalleryItem> {
        return this.groupBy { it.folderPath }
            .map { (folderPath, mediaItems) ->
                GalleryItem(folderPath, mediaItems[0].folderName, mediaItems)
            }.toMutableList().also {
                it.add(0, transformToImageItems())
                it.add(1, transformToVideoItems())
            }
    }

    private fun List<MediaItem>.transformToImageItems(): GalleryItem {
        return this.filter {
            it.fileName.isImageFile()
        }.let { list ->
            GalleryItem(UUID.randomUUID().toString(), "All Images", list)
        }
    }

    private fun List<MediaItem>.transformToVideoItems(): GalleryItem {
        return this.filter {
            it.fileName.isVideoFile()
        }.let { list ->
            GalleryItem(UUID.randomUUID().toString(), "All Videos", list)
        }
    }


    fun String.isImageFile() =
        this.endsWith(".jpg", ignoreCase = true) ||
                this.endsWith(".jpeg", ignoreCase = true) ||
                this.endsWith(".png", ignoreCase = true)
    // Add other image extensions as needed


    private fun String.isVideoFile() =
        this.endsWith(
            ".mp4",
            ignoreCase = true
        ) || this.endsWith(".avi", ignoreCase = true) || this.endsWith(
            ".mov",
            ignoreCase = true
        )// Add other video extensions as needed

}
