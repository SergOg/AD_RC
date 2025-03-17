package ru.gb.rc

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import ru.gb.rc.data.Device
import ru.gb.rc.data.DeviceDao

class DevicePultViewModel(
    private val deviceDao: DeviceDao,
    @Assisted val id: Int
) : ViewModel() {
    suspend fun pultView(device: Device){
        val device = deviceDao.getOne(id)
    }
}