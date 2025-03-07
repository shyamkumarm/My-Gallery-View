package com.gallery.myapplication.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.setSingletonImageLoaderFactory
import com.gallery.myapplication.R
import com.gallery.myapplication.presentation.screen.MyNavigationScreen
import com.gallery.myapplication.presentation.screen.ShowErrorContent
import com.gallery.myapplication.ui.theme.MyApplicationTheme
import com.gallery.myapplication.utils.CoilImageLoader
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val granted = permissions.values.all { it }
                setContent {
                    if (granted) {
                        setSingletonImageLoaderFactory { context ->
                            CoilImageLoader.getAsyncImageLoader(context)
                        }
                        MyApplicationTheme() {
                            val viewModel: MyGalleryViewModel by viewModel()
                            Scaffold(topBar = { TopAppBarDefaults() }) { padding ->
                                MyNavigationScreen(viewModel, Modifier.padding(padding))
                            }
                        }
                    } else {
                        ShowErrorContent(errorMessage = "Oops! Permission Denied, Close the Application, Go to Setting, Enable the Permission")
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

    @Preview(showBackground = true)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarDefaults() {
        TopAppBar(
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