package com.gallery.myapplication.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem


@Composable
fun FolderListScreen(fileItems: List<GalleryItem>, onFolderClick: (String) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(items = fileItems, key = { it.folderId }) { it ->
            FolderItem(it.folderName, it.fileList) {
                onFolderClick(it.folderId)
            }
        }
    }
}

@Composable
fun FileListScreen(fileItems: List<MediaItem>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(items = fileItems) {
            FileItem(it) {
                // event required when item clicked
            }
        }
    }
}

@Composable
fun FolderItem(
    folderName: String,
    fileList: MutableList<MediaItem>,
    onMediaItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onMediaItemClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            model = fileList[0].uri,
            contentDescription = "Media",
            Modifier
                .fillMaxSize(),
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
            .padding(4.dp)
            .clickable { onFolderClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            model = mediaItem.uri,
            contentDescription = "Media",
            Modifier
                .fillMaxSize(),
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