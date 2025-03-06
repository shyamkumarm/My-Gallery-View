package com.gallery.myapplication.utils

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem

object CoilImageLoader {


    fun getAsyncImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(200)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache { MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build() }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache { newDiskCache() }
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }


    private fun newDiskCache(): DiskCache {
        return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "media_cache")
            .maxSizeBytes(1024L * 1024 * 1500)
            .build()
    }
}