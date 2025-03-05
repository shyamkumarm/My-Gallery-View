package com.gallery.myapplication.presentation

import android.Manifest
import android.app.Application
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

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
        val mediaItems by viewModel.mediaItems.collectAsState()
        LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)) {
            items(mediaItems.size) { index ->
                Card(
                    modifier = Modifier.padding(4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    MediaItem(pair = mediaItems[index])
                }
            }
        }
    }


    @Composable
    fun MediaItem(pair: Pair<Uri, String>) {
        AsyncImage(
            model = pair.first, contentDescription = "Picture",
            Modifier.fillMaxSize(), contentScale = ContentScale.Crop
        )
        Text(
            text = pair.second,
            style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp)
        )
    }

    @Preview(showBackground = true)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarDefaults() {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.surface,
            ),
            title = {
                Text("My Gallery View")
            }
        )
    }
}