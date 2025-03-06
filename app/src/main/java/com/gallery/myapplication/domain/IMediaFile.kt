package com.gallery.myapplication.domain

interface IMediaFile {

    suspend fun fetchMedia(): Result<List<MediaItem>>
}