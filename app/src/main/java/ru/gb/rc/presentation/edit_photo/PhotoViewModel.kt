package ru.gb.rc.presentation.edit_photo

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.gb.rc.data.Attractions
import ru.gb.rc.data.AttractionsDao
import ru.gb.rc.data.CommandId
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.presentation.edit_device.EditDeviceViewState
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor

private const val FILENAME_FORMAT = "yyy-MM-dd-HH-mm-ss"

//@HiltViewModel(assistedFactory = PhotoViewModel.Factory::class)
class PhotoViewModel @AssistedInject constructor(
//    private val activity: Activity,
    private val deviceDao: DeviceDao,
    private val attractionsDao: AttractionsDao,
    @Assisted val id: Int
) : ViewModel() {

    val allPhotos = this.attractionsDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

//    @AssistedFactory
//    interface Factory {
//        fun create(id: Int): PhotoViewModel
//    }

//    private var imageCapture: ImageCapture? = null
//    private lateinit var executor: Executor
//    private val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
//        .format(System.currentTimeMillis())

    private val _state = MutableLiveData<PhotoViewState>(PhotoViewState())
    private val state: LiveData<PhotoViewState> = _state

    private val _closeScreenEvent = Channel<Unit>(capacity = Channel.UNLIMITED)
    val closeScreenEvent = _closeScreenEvent.receiveAsFlow()

    init {
        init(id)
    }

    private fun init(id: Int) {
        Log.d("PhotoViewModel", id.toString())
        viewModelScope.launch {
            if (id == 0) {   // если id==0, обработка полученного устройства
                _state.value = PhotoViewState()
            } else {        // либо обновить текущий по его id
                val device = deviceDao.getOne(id)
                device?.let {
                    _state.value = PhotoViewState(
                        it.id,
                        it.location,
                        it.imgSrc,
                        it.protocol,
                        it.equipment
                    )
                }
            }
        }
    }

//    fun takePhotoBtn(context: Context) {
//        val imageCapture = imageCapture ?: return
//
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
//            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
//        }
//
//        val outputOptions = context.let {
//            ImageCapture.OutputFileOptions.Builder(
//                it.contentResolver,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                contentValues
//            )
//                .build()
//        }
//
//        if (outputOptions != null) {
//            imageCapture.takePicture(
//                outputOptions,
//                executor,
//                object : ImageCapture.OnImageSavedCallback {
//                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                        Toast.makeText(
//                            context,
//                            "Photo saved on: ${outputFileResults.savedUri}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        val uri = (outputFileResults.savedUri).toString()
//                        onAddSrc(name, uri)
//                        activity.finish()
//                    }
//
//                    override fun onError(exception: ImageCaptureException) {
//                        Toast.makeText(
//                            context,
//                            "Photo failed: ${exception.message}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        exception.printStackTrace()
//                    }
//                })
//        }
//    }

    private fun onAddSrc(
        date: String,
        uri: String,
    ) {
        viewModelScope.launch {
            state.value?.let {
                deviceDao.updateColumn(
                    id = id,
                    imgSrc = uri,
                )
            }
        }
    }

    fun onDelSrc() {
        viewModelScope.launch {
            state.value?.let {
                deviceDao.updateColumn(
                    id = id,
                    imgSrc = "",
                )
            }
        }
    }

    fun onAddBtn(date: String, uri: String) {
        viewModelScope.launch {
//            attractionsDao.insert(Attractions(date = date, uri = uri))
            onAddSrc(date, uri)
        }
    }
}