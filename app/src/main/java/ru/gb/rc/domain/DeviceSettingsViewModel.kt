package ru.gb.rc.domain

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
import ru.gb.rc.data.SettingsDevice
import ru.gb.rc.data.SettingsDeviceDao

@HiltViewModel(assistedFactory = DeviceSettingsViewModel.Factory::class)
class DeviceSettingsViewModel @AssistedInject constructor(
    private val settingsDeviceDao: SettingsDeviceDao,
    @Assisted val id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DeviceSettingsViewModel
    }

    private val settingsDeviceList: MutableList<SettingsDevice> = mutableListOf()
    private val _state = MutableLiveData<DeviceSettingsViewState>(DeviceSettingsViewState())
    val state: LiveData<DeviceSettingsViewState> = _state

    private val _closeScreenEvent = Channel<Unit>(capacity = Channel.UNLIMITED)
    val closeScreenEvent = _closeScreenEvent.receiveAsFlow()

    init {
        init(id)
    }

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
            state.value?.let { viewState ->
                val powerButtonSettings = settingsDeviceList.find { it.commandId == CommandId.powerButton.name }
                if(powerButtonSettings != null && powerButton.isNotEmpty()){
                    settingsDeviceDao.update(powerButtonSettings.copy(content = powerButton))
                }
                if(powerButtonSettings != null && powerButton.isEmpty()){
                    settingsDeviceDao.delete(powerButtonSettings.copy(deviceId = ))
                }
                if(powerButtonSettings == null && powerButton.isNotEmpty()){
                    settingsDeviceDao.insert(SettingsDevice(null, deviceId = , commandId = , content = ))
                }
            }
            _closeScreenEvent.send(Unit)
        }
    }

    fun init(id: Int) {
        Log.d("EditDeviceViewModel", id.toString())
        viewModelScope.launch {
            val list = settingsDeviceDao.getAllCommands(id)
            settingsDeviceList.addAll(list)
            _state.value = DeviceSettingsViewState(
                powerButton = list.find { it.commandId == CommandId.powerButton.name }?.content ?: "",
                muteButton = list.find { it.commandId == CommandId.muteButton.name }?.content ?: "",
                oneButton = list.find { it.commandId == CommandId.oneButton.name }?.content ?: "",
                twoButton = list.find { it.commandId == CommandId.twoButton.name }?.content ?: "",
                threeButton = list.find { it.commandId == CommandId.threeButton.name }?.content ?: "",
                fourButton = list.find { it.commandId == CommandId.fourButton.name }?.content ?: "",
                upButton = list.find { it.commandId == CommandId.upButton.name }?.content ?: "",
                downButton = list.find { it.commandId == CommandId.downButton.name }?.content ?: "",
                minusButton = list.find { it.commandId == CommandId.minusButton.name }?.content ?: "",
                plusButton = list.find { it.commandId == CommandId.plusButton.name }?.content ?: "",
            )
        }
    }
}