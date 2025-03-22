package com.gallery.myapplication.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gallery.myapplication.presentation.GalleryUiState
import com.gallery.myapplication.presentation.MyGalleryViewModel
import kotlinx.serialization.Serializable

@Composable
fun MyNavigationScreen(
    viewModel: MyGalleryViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val folderItems by viewModel.mediaItems.collectAsState()
    val fileItems by viewModel.fileItems.collectAsState()
    NavHost(
        navController = navController,
        startDestination = MyGalleryScreen.FolderScreen

    ) {
        composable<MyGalleryScreen.FolderScreen> {
            when (folderItems) {
                is GalleryUiState.Success -> {
                    FolderListScreen(fileItems = (folderItems as GalleryUiState.Success).mediaItem,modifier)
                    { selectedFolder, selectedId ->
                        viewModel.getFileItems(selectedId)
                        navController.navigate(MyGalleryScreen.FileScreen(selectedFolder))
                    }
                }
                is GalleryUiState.Error -> ShowErrorContent(errorMessage = (folderItems as GalleryUiState.Error).message)
                else -> ShowLoading()
            }

        }
        composable<MyGalleryScreen.FileScreen> {
            when (fileItems) {
                is GalleryUiState.Success -> {
                    val folderName = it.toRoute<MyGalleryScreen.FileScreen>().folderName
                    FileListScreen(
                        folderName = folderName,
                        fileItems = (fileItems as GalleryUiState.Success).mediaItem
                        ,modifier = modifier
                    )
                }

                is GalleryUiState.Error -> ShowErrorContent(errorMessage = (fileItems as GalleryUiState.Error).message)
                else -> ShowLoading()
            }

        }

    }
}

sealed class MyGalleryScreen {
    @Serializable
    data object FolderScreen : MyGalleryScreen()

    @Serializable
    data class FileScreen(val folderName: String) : MyGalleryScreen()
}


