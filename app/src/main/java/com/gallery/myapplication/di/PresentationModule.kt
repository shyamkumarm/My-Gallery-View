package com.gallery.myapplication.di

import com.gallery.myapplication.presentation.MyGalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val requestViewModel = module {
    viewModel { MyGalleryViewModel(get()) }
}

private val appModule = module {
    single { } // app utils if any

}

val presentationModules = listOf(requestViewModel)