package ru.gb.rc.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.gb.rc.data.Device
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.data.NewDevice
import ru.gb.rc.data.SettingsDeviceDao
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val deviceDao: DeviceDao, private val settingsDeviceDao: SettingsDeviceDao) : ViewModel() {

    val allDevices = this.deviceDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

//    fun onAddBtn(location: String, protocol: String, equipment: String) {
////        val size: Int = allDevices.value.size
//        viewModelScope.launch {
//            deviceDao.insert(
//                NewDevice(
//                    location = location,
//                    imgSrc = "",
//                    protocol = protocol,
//                    equipment = equipment,
//                )
//            )
//        }
//    }

    fun onDeleteBtn(device: Device) {
        viewModelScope.launch {
            deviceDao.delete(device)
            settingsDeviceDao.deleteById(device.id)
        }
    }
}