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

@HiltViewModel(assistedFactory = EditDeviceViewModel.Factory::class)
class EditDeviceViewModel @AssistedInject constructor(private val deviceDao: DeviceDao, @Assisted val id: Int) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): EditDeviceViewModel
    }
//    init {
//        Log.d("EditDeviceViewModel", id.toString())
//    }
    private val _state = MutableLiveData<EditDeviceViewState>(EditDeviceViewState())
    private val state: LiveData<EditDeviceViewState> = _state

    fun onAddBtn() {//
//        viewModelScope.launch {
//            deviceDao.insert(
//                NewDevice(
//                    location = state.value.location,
//                    imgSrc = "",
//                    protocol = state.value.protocol,
//                    device = state.value.device
//                )
//            )
//        }
    }

    fun init(id: Int) {
        Log.d("EditDeviceViewModel", id.toString())
        viewModelScope.launch {
            val device = deviceDao.getOne(id)
            if (device != null) {
                if (device.id == -1){   //если id==-1, либо обновить текущий по его id
                    deviceDao.update(device.copy(id = device.id))
                    // Обработка полученного устройства
                    _state.value = _state.value?.copy(device = device.toString())
                }else{
                    //отобразить в полях ввода его значения
                    location.text = device.location
                    equipment.text = device.equipment
                    protocol.text = device.protocol
                }
            }else{
                deviceDao.insert(Device(id, "location", "", "protocol", "device"))
            }
        }
    }
}