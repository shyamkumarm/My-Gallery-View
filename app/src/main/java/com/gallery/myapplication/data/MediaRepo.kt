package com.gallery.myapplication.data

import com.gallery.myapplication.domain.IMediaFile
import com.gallery.myapplication.domain.MediaItem

class MediaRepo(private val source: MediaSource) : IMediaFile {


    override suspend fun fetchMedia(): Result<List<MediaItem>> {
        return source.fetchMediaSource()
    }
}
