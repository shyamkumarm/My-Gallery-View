package com.gallery.myapplication.presentation

import android.Manifest
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.gallery.myapplication.R
import com.gallery.myapplication.domain.MediaItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val granted = permissions.values.all { it }
                if (granted) {
                    setContent {
                        MaterialTheme {
                            Scaffold(topBar = { TopAppBarDefaults() }) { innerPadding ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                ) {
                                    val viewModel = viewModel<MyGalleryViewModel>(
                                        factory = MyGalleryViewModelFactory(application)
                                    )
                                    GalleryScreen(viewModel)
                                }
                            }
                        }
                    }
                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
            )
        } else {
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            )
        }
    }

    class MyGalleryViewModelFactory(private val application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyGalleryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MyGalleryViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    @Composable
    fun GalleryScreen(viewModel: MyGalleryViewModel) {
        val folderItems by viewModel.mediaItems.collectAsState()
        val fileItems by viewModel.fileItems.collectAsState()
        var selectedFolder by remember {
            mutableIntStateOf(0)
        }
        if (selectedFolder == 0) {
            Column {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(folderItems.size) { index ->
                        val folderName = folderItems[index].folderName
                        val fileList = folderItems[index].fileList
                        FolderItem(folderName, fileList) {
                            selectedFolder = 1
                            viewModel.getFileItems(folderItems[index].folderId)
                        }
                    }
                }
            }
        } else {
            Column {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(items = fileItems) { it ->
                        FileItem(it) {
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun FolderItem(
        folderName: String,
        fileList: MutableList<MediaItem>,
        onFolderClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = fileList[0].uri,
                contentDescription = "Media",
                Modifier
                    .fillMaxSize()
                    .clickable { onFolderClick() },
                contentScale = ContentScale.Crop
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = folderName,
                    style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = fileList.size.toString(),
                    style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp)
                )
            }
        }
    }


    @Composable
    fun FileItem(
        mediaItem: MediaItem,
        onFolderClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = mediaItem.uri,
                contentDescription = "Media",
                Modifier
                    .fillMaxSize()
                    .clickable { onFolderClick() },
                contentScale = ContentScale.Crop
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = mediaItem.fileName,
                    style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp)
                )
            }
        }
    }


    @Preview(showBackground = true)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarDefaults() {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(text = stringResource(R.string.app_name), maxLines = 1)
            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            }
        )
    }
}