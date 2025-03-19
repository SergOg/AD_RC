package ru.gb.rc

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
import ru.gb.rc.data.CommandId
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.data.SettingsDevice
import ru.gb.rc.data.SettingsDeviceDao
import ru.gb.rc.domain.DeviceSettingsViewState

@HiltViewModel(assistedFactory = DevicePultViewModel.Factory::class)
class DevicePultViewModel @AssistedInject constructor(
//    private val deviceDao: DeviceDao,
    private val settingsDeviceDao: SettingsDeviceDao,
    @Assisted val id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DevicePultViewModel
    }

    private val settingsDeviceList: MutableList<SettingsDevice> = mutableListOf()
    private val _state = MutableLiveData<DevicePultViewState>(DevicePultViewState())
    val state: LiveData<DevicePultViewState> = _state

//    private val _closeScreenEvent = Channel<Unit>(capacity = Channel.UNLIMITED)
//    val closeScreenEvent = _closeScreenEvent.receiveAsFlow()

    init {
        init(id)
    }

    fun init(id: Int) {
        Log.d("DevicePultViewModel", id.toString())
        viewModelScope.launch {
            val list = settingsDeviceDao.getAllCommands(id)
            settingsDeviceList.addAll(list)
            _state.value = DevicePultViewState(
                powerButton = list.find { it.commandId == CommandId.powerButton.name }?.content
                    ?: "",
                muteButton = list.find { it.commandId == CommandId.muteButton.name }?.content ?: "",
                oneButton = list.find { it.commandId == CommandId.oneButton.name }?.content ?: "",
                twoButton = list.find { it.commandId == CommandId.twoButton.name }?.content ?: "",
                threeButton = list.find { it.commandId == CommandId.threeButton.name }?.content
                    ?: "",
                fourButton = list.find { it.commandId == CommandId.fourButton.name }?.content ?: "",
                upButton = list.find { it.commandId == CommandId.upButton.name }?.content ?: "",
                downButton = list.find { it.commandId == CommandId.downButton.name }?.content ?: "",
                minusButton = list.find { it.commandId == CommandId.minusButton.name }?.content
                    ?: "",
                plusButton = list.find { it.commandId == CommandId.plusButton.name }?.content ?: "",
            )
//            val device = deviceDao.getOne(id)
//            device?.let {
//                _state.value = DevicePultViewState(
//                    it.id,
//                    it.location,
//                    it.imgSrc,
//                    it.protocol,
//                    it.equipment
//                )
//            }
        }
    }
}
