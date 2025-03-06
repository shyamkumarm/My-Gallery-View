package com.gallery.myapplication.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gallery.myapplication.presentation.MyGalleryViewModel
import kotlinx.serialization.Serializable

@Composable
fun MyNavigationScreen(
    viewModel: MyGalleryViewModel,
    modifier: Modifier  = Modifier
) {
    val navController = rememberNavController()
    val folderItems by viewModel.mediaItems.collectAsState()
    val fileItems by viewModel.fileItems.collectAsState()
    NavHost(
        navController = navController,
        startDestination = MyGalleryScreen.FolderScreen

    ) {
        composable<MyGalleryScreen.FolderScreen>(
        ) {
            FolderListScreen(fileItems = folderItems) { selectedFolder ->
                viewModel.getFileItems(selectedFolder)
                navController.navigate(MyGalleryScreen.FileScreen)
            }
        }
        composable<MyGalleryScreen.FileScreen>() {
            FileListScreen(fileItems = fileItems)
        }

    }
}


sealed class MyGalleryScreen {
    @Serializable
    data object FolderScreen : MyGalleryScreen()

    @Serializable
    data object FileScreen : MyGalleryScreen()
}


