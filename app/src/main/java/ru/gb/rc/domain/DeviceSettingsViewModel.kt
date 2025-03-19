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
import ru.gb.rc.data.NewSettingsDevice
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

    fun init(id: Int) {
        Log.d("EditDeviceViewModel", id.toString())
        viewModelScope.launch {
            val list = settingsDeviceDao.getAllCommands(id)
            settingsDeviceList.addAll(list)
            _state.value = DeviceSettingsViewState(
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
        }
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
            state.value?.let {
                actionButtonSave(powerButton, CommandId.powerButton)
                actionButtonSave(muteButton, CommandId.muteButton)
                actionButtonSave(oneButton, CommandId.oneButton)
                actionButtonSave(twoButton, CommandId.twoButton)
                actionButtonSave(threeButton, CommandId.threeButton)
                actionButtonSave(fourButton, CommandId.fourButton)
                actionButtonSave(upButton, CommandId.upButton)
                actionButtonSave(downButton, CommandId.downButton)
                actionButtonSave(minusButton, CommandId.minusButton)
                actionButtonSave(plusButton, CommandId.plusButton)
            }
            _closeScreenEvent.send(Unit)
        }
    }

    private suspend fun actionButtonSave(anyButton: String, anyCommandButton: CommandId) {
        val anyButtonSettings = settingsDeviceList.find { it.commandId == anyCommandButton.name }
        if (anyButtonSettings != null && anyButton.isNotEmpty()) {
            settingsDeviceDao.update(anyButtonSettings.copy(content = anyButton))
        }
        if (anyButtonSettings != null && anyButton.isEmpty()) {
            settingsDeviceDao.deleteById(id)
        }
        if (anyButtonSettings == null && anyButton.isNotEmpty()) {
            settingsDeviceDao.insert(
                NewSettingsDevice(
                    deviceId = id,
                    commandId = anyCommandButton.toString(),
                    content = anyButton,
                )
            )
        }
    }
}