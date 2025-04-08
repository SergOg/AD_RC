package ru.gb.rc.presentation.edit_photo

import android.util.Log
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

    fun onAddSrc(
        name: String,
        uri: String,
    ) {
        viewModelScope.launch {
            state.value?.let {
                deviceDao.updateColumn(
                    id = id,
                    imgSrc = uri,
                )
            }
            _closeScreenEvent.send(Unit)
        }
    }

//    fun onAddBtn(name: String, uri: String) {
//        viewModelScope.launch {
//            onAddSrc(name, uri)
//        }
//    }
}