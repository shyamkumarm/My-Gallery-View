package com.gallery.myapplication.presentation.screen

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import com.gallery.myapplication.R
import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import com.gallery.myapplication.utils.CoilImageLoader


@Composable
fun FolderListScreen(
    fileItems: List<GalleryItem>,
    onFolderClick: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier,
        columns = GridCells.Fixed(4)
    ) {
        items(items = fileItems, key = { it.folderId }) { it ->
            FolderItem(it.folderName, it.fileList) {
                onFolderClick(it.folderId)
            }
        }
    }
}

@Composable
fun FileListScreen(fileItems: List<MediaItem>) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier,
        contentPadding = PaddingValues(10.dp),
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 5.dp,
        horizontalArrangement = Arrangement.SpaceAround
    ){
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
    fileList: List<MediaItem>,
    onMediaItemClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .clickable { onMediaItemClick() },
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fileList[0].uri)
                    .size(Size(200,200)) // Set the target size to load the image at.
                    .build(),
                contentDescription = "Media",
                   imageLoader = CoilImageLoader.getAsyncImageLoader(LocalContext.current),
                contentScale = ContentScale.Crop
            )
        }
        Row {
            Text(
                text = folderName,
                style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(4.dp)
            )
            Text(
                text = fileList.size.toString(),
                style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(4.dp)
            )
        }
    }
}

    @Composable
    fun FileItem(
        mediaItem: MediaItem,
        onMediaItemClick: () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onMediaItemClick() },
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = mediaItem.uri,
                    contentDescription = "Media",
                    contentScale = ContentScale.Crop
                )
            }
            Row {
                Text(
                    text =  mediaItem.fileName,
                    style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(4.dp)
                )
            }
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
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
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
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    )
                )
            ),

            GalleryItem(
                folderId = "3", folderName = "All Images",
                fileList = listOf(
                    MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    )
                )
            ),

            GalleryItem(
                folderId = "4", folderName = "All Images",
                fileList = listOf(
                    MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_background),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    ), MediaItem(
                        uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.drawable.ic_launcher_foreground),
                        fileName = "IMG_20210509_164609", folderName = "Camera"
                    )
                )
            )
        )
    ) {
        FolderListScreen(fileItems) {

        }
    }