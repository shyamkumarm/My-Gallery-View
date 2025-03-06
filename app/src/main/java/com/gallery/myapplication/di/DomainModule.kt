package com.gallery.myapplication.di

import com.gallery.myapplication.domain.MediaUseCase
import org.koin.dsl.module


private val domainModule = module {
    single { MediaUseCase(get()) }

}

val domainModules = listOf(domainModule)