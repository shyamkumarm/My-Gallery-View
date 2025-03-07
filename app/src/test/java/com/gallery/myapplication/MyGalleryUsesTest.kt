import android.net.Uri
import com.gallery.myapplication.domain.GalleryItem
import com.gallery.myapplication.domain.MediaItem
import com.gallery.myapplication.domain.MediaUseCase
import com.gallery.myapplication.presentation.FileUiState
import com.gallery.myapplication.presentation.FolderUiState
import com.gallery.myapplication.presentation.MyGalleryViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MyGalleryUsesTest {

    private lateinit var viewModel: MyGalleryViewModel
    private lateinit var useCase: MediaUseCase
    private val mockUri = Mockito.mock(Uri::class.java)

    @Before
    fun setup() {
        useCase = mock(MediaUseCase::class.java)
        viewModel = MyGalleryViewModel(useCase)
    }

    @Test
    fun `mediaItems should emit Loading state initially`()  {
        assertEquals(FolderUiState.Loading, viewModel.mediaItems.value)
    }

    @Test
    fun `fetch file items successfully`() {
        val folderId = "1"
        val mockFiles = listOf(MediaItem("/path", mockUri, "file1", "Folder1"))
        val mockGalleryItems = listOf(GalleryItem(folderId, "Folder1", emptyList()))

         Mockito.`when`(useCase.getFileItems(mockGalleryItems, folderId))
            .thenReturn(flowOf(FileUiState.Success(mockFiles)))
    }
}
