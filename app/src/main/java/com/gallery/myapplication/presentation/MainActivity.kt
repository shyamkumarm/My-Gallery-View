package com.gallery.myapplication.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil3.compose.setSingletonImageLoaderFactory
import com.gallery.myapplication.R
import com.gallery.myapplication.presentation.screen.MyNavigationScreen
import com.gallery.myapplication.presentation.screen.ShowErrorContent
import com.gallery.myapplication.ui.theme.MyApplicationTheme
import com.gallery.myapplication.utils.CoilImageLoader
import org.koin.androidx.compose.koinViewModel

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
                        MyApplicationTheme {
                            Scaffold { padding ->
                                MyNavigationScreen(viewModel = koinViewModel(), Modifier.padding(padding))
                            }
                        }
                    } else {
                        ShowErrorContent(errorMessage = stringResource(R.string.permission_error))
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


}