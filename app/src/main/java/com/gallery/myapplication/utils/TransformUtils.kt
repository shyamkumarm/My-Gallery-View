package com.gallery.myapplication.utils

import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import java.util.UUID

object TransformUtils {

     fun List<MediaItem>.transformToFolderItems(): List<GalleryItem> {
        return this.groupBy { it.folderName }
            .map { (folderName, mediaItems) ->
                GalleryItem(UUID.randomUUID().toString(), folderName, mediaItems.toMutableList())
            }
    }

     fun List<MediaItem>.transformToImageItems(): List<GalleryItem> {
        return this.filter {
            it.fileName.endsWith(".jpg", ignoreCase = true) ||
                    it.fileName.endsWith(".jpeg", ignoreCase = true) ||
                    it.fileName.endsWith(".png", ignoreCase = true) /*||
                    it.fileName.endsWith(".gif", ignoreCase = true)*/
        } // Add other image extensions as needed
            .groupBy { "All Images" }
            .map { (folderName, mediaItems) ->
                GalleryItem(UUID.randomUUID().toString(), folderName, mediaItems.toMutableList())
            }
    }

     fun List<MediaItem>.transformToVideoItems(): List<GalleryItem> {
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
                GalleryItem(UUID.randomUUID().toString(), folderName, mediaItems.toMutableList())
            }
    }
}