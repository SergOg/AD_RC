package ru.gb.rc.domain

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
import ru.gb.rc.data.Device
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.data.NewDevice
import ru.gb.rc.data.SettingsDevice
import ru.gb.rc.presentation.edit_device.EditDeviceViewModel
import ru.gb.rc.presentation.edit_device.EditDeviceViewState

@HiltViewModel(assistedFactory = DeviceSettingsViewModel.Factory::class)
class DeviceSettingsViewModel @AssistedInject constructor(
    private val deviceDao: DeviceDao,
    @Assisted val id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DeviceSettingsViewModel
    }

    private val _state = MutableLiveData<DeviceSettingsViewState>(DeviceSettingsViewState())
    val state: LiveData<DeviceSettingsViewState> = _state

    private val _closeScreenEvent = Channel<Unit>(capacity = Channel.UNLIMITED)
    val closeScreenEvent = _closeScreenEvent.receiveAsFlow()

    fun onSaveBtn(
        powerButton: String,
        muteButton: String,
        oneButton: String,
        twoButton: String,
        threeButton: String,
        fourButton: String,
        upButton: String,
        downButton: String,
        minusButton: String,
        plusButton: String,
    ) {
        viewModelScope.launch {
//            state.value?.let { device ->
//                if (device.id == null) {
//                    deviceDao.insert(
//                        SettingsDevice(
//                            deviceId = device.id,
//                            commandId = "powerButton",
//                            content = powerButton,
//                        )
//                    )
//                } else {
//                    deviceDao.update(
//                        SettingsDevice(
//                            deviceId = device.id,
//                            commandId = "powerButton",
//                            content = powerButton,
//                        )
//                    )
//                }
//            }
            _closeScreenEvent.send(Unit)
        }
    }
}