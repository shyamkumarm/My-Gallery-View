package com.gallery.myapplication.di

import com.gallery.myapplication.data.MediaRepo
import com.gallery.myapplication.data.MediaSource
import com.gallery.myapplication.domain.IMediaFile
import org.koin.dsl.module


val mediaModules = module {
    single { MediaSource(get()) }
    single { getMediaRepository(get<MediaSource>()) }
}

fun getMediaRepository(remoteDataSource: MediaSource): IMediaFile = MediaRepo(remoteDataSource)

val dataModules = listOf(mediaModules)