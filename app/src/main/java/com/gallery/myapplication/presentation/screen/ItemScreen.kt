package com.gallery.myapplication.presentation.screen

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.gallery.myapplication.FolderUiState
import com.gallery.myapplication.FolderUiState.Error
import com.gallery.myapplication.R
import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import com.gallery.myapplication.utils.TransformUtils.isImageFile


@Composable
fun FolderListScreen(
    fileItems: List<GalleryItem>,
    onFolderClick: (String, String) -> Unit
) {
    /* Column {
         Text(
            " fileItems[0].folderName",
             modifier = Modifier.padding(16.dp),
             style = MaterialTheme.typography.titleLarge
         )*/
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(8.dp)) {
        items(items = fileItems, key = { it.folderId }) { it ->
            FolderItem(it.folderName, it.fileList) {
                onFolderClick(it.folderName, it.folderId)
            }
        }
    }
    // }
}

@Composable
fun FileListScreen(folderName: String, fileItems: List<MediaItem>) {
    Column {
        Text(
            folderName,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(8.dp)) {
            items(items = fileItems) {
                FileItem(it) {
                    // event required when item clicked
                }
            }
        }
    }
}

@Composable
fun FolderItem(
    folderName: String,
    fileList: List<MediaItem>,
    onMediaItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CardImageScreen(onMediaItemClick, fileList[0])
        val mTextModifier = Modifier
            .align(Alignment.Start)
            .padding(start = 8.dp)
        Text(
            text = folderName,
            modifier = mTextModifier,
            maxLines = 1,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(fileList.size.toString(), modifier = mTextModifier, fontSize = 10.sp)
    }
}

@Composable
private fun CardImageScreen(
    onMediaItemClick: () -> Unit,
    file: MediaItem
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onMediaItemClick() }
            .aspectRatio(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AsyncImage(
                model = file.uri,
                contentDescription = "Media",
                contentScale = ContentScale.Crop,
            )
            if (!file.fileName.isImageFile()) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.TwoTone.PlayArrow,
                    contentDescription = null, modifier = Modifier.size(29.dp)
                )
            }
        }
    }
}

@Composable
fun FileItem(
    mediaItem: MediaItem,
    onMediaItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CardImageScreen(onMediaItemClick, mediaItem)
        Text(
            mediaItem.fileName,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp),
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun ShowLoading() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowErrorContent(folderItems: FolderUiState) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = (folderItems as Error).message,
            style = MaterialTheme.typography.displayLarge
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FolderListPreview(
    fileItems: List<GalleryItem> = listOf(
        GalleryItem(
            folderId = "1", folderName = "All Images",
            fileList = listOf(
                MediaItem(
                    uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                    fileName = "IMG_20210509_164609", folderName = "Camera"
                )
            )
        ),

        GalleryItem(
            folderId = "2", folderName = "All Images",
            fileList = listOf(
                MediaItem(
                    uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                    fileName = "IMG_20210509_164609", folderName = "Camera"
                )
            )
        ),

        GalleryItem(
            folderId = "3", folderName = "All files",
            fileList = listOf(
                MediaItem(
                    uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                    fileName = "IMG_20210509_164609.jpg", folderName = "Camera"
                )
            )
        ),

        GalleryItem(
            folderId = "4", folderName = "All Images",
            fileList = listOf(
                MediaItem(
                    uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                    fileName = "IMG_20210509_164609", folderName = "Camera"
                )
            )
        )
    )
) {
    FolderListScreen(fileItems) { _, _ ->
    }
}