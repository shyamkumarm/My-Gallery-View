package com.gallery.myapplication.domain

interface IMediaFile {

    suspend fun fetchMedia(): List<MediaItem>
}