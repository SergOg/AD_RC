package ru.gb.rc.presentation.edit_photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.presentation.edit_device.EditDeviceViewState

@HiltViewModel(assistedFactory = PhotoViewModel.Factory::class)
class PhotoViewModel @AssistedInject constructor(
    private val deviceDao: DeviceDao,
    @Assisted val id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): PhotoViewModel
    }

    private val _state = MutableLiveData<PhotoViewState>(PhotoViewState())
    private val state: LiveData<PhotoViewState> = _state

    private val _closeScreenEvent = Channel<Unit>(capacity = Channel.UNLIMITED)
    val closeScreenEvent = _closeScreenEvent.receiveAsFlow()

    fun onAddSrc(
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
}