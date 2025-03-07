package ru.gb.rc.presentation.edit_device

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.gb.rc.data.Device
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.data.NewDevice

@HiltViewModel(assistedFactory = EditDeviceViewModel.Factory::class)
class EditDeviceViewModel @AssistedInject constructor(
    private val deviceDao: DeviceDao,
    @Assisted val id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): EditDeviceViewModel
    }

    private val _state = MutableLiveData<EditDeviceViewState>(EditDeviceViewState())
    val state: LiveData<EditDeviceViewState> = _state

    fun onAddBtn(
        location : String,
        protocol : String,
        equipment : String
    ) {//
        viewModelScope.launch {
            state.value?.let { device ->
                if (device.id == null) {
                    deviceDao.insert(
                        NewDevice(
                            location = location,
                            imgSrc = "",
                            protocol = protocol,
                            equipment = equipment
                        )
                    )
                } else {
                    deviceDao.update(
                        Device(
                            id = device.id,
                            location = location,
                            imgSrc = "",
                            protocol = protocol,
                            equipment = equipment
                        )
                    )
                }
            }
        }
    }

    fun init(id: Int) {
        Log.d("EditDeviceViewModel", id.toString())
        viewModelScope.launch {
            if (id == 0) {   //если id==0, либо обновить текущий по его id
                // Обработка полученного устройства
                _state.value = EditDeviceViewState()
            } else {
                val device = deviceDao.getOne(id)
                device?.let {
                    _state.value = EditDeviceViewState(
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
}