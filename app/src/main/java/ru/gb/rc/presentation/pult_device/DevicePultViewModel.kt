package ru.gb.rc.presentation.pult_device

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

@HiltViewModel(assistedFactory = DevicePultViewModel.Factory::class)
class DevicePultViewModel @AssistedInject constructor(
    private val settingsDeviceDao: SettingsDeviceDao,
    private val deviceDao: DeviceDao,
    @Assisted val id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DevicePultViewModel
    }

    private val settingsDeviceList: MutableList<SettingsDevice> = mutableListOf()
    private val _state = MutableLiveData(DevicePultViewState())
    val state: LiveData<DevicePultViewState> = _state

    private val _closeScreenEvent = Channel<Unit>(capacity = Channel.UNLIMITED)
    val closeScreenEvent = _closeScreenEvent.receiveAsFlow()

    private val _toastScreenEvent = Channel<String>(capacity = Channel.UNLIMITED)
    val toastScreenEvent = _toastScreenEvent.receiveAsFlow()

//    init {
//        init(id)
//    }

    fun init() {
        viewModelScope.launch {
            val list = settingsDeviceDao.getAllCommands(id)
            val device = deviceDao.getOne(id)
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
                namePult = device?.equipment ?: ""
            )
        }

    }

    fun powerButtonClicked() {
        val a = state.value?.powerButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.powerButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for powerButton: $a") }
        }
    }

    fun muteButtonClicked() {
        val a = state.value?.muteButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.muteButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for muteButton: $a") }
        }
    }

    fun oneButtonClicked() {
        val a = state.value?.oneButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.oneButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for oneButton: $a") }
        }
    }

    fun twoButtonClicked() {
        val a = state.value?.twoButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.twoButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for twoButton: $a") }
        }
    }

    fun threeButtonClicked() {
        val a = state.value?.threeButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.threeButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for threeButton: $a") }
        }
    }

    fun fourButtonClicked() {
        val a = state.value?.fourButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.fourButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for fourButton: $a") }
        }
    }

    fun upButtonClicked() {
        val a = state.value?.upButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.upButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for upButton: $a") }
        }
    }

    fun downButtonClicked() {
        val a = state.value?.downButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.downButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for downButton: $a") }
        }
    }

    fun minusButtonClicked() {
        val a = state.value?.minusButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.minusButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for minusButton: $a") }
        }
    }

    fun plusButtonClicked() {
        val a = state.value?.plusButton
        if (a?.isNotEmpty() == true) {
//            Log.d("DevicePultViewModelCommand", state.value?.plusButton!!)
            viewModelScope.launch { _toastScreenEvent.send("Sending command for plusButton: $a") }
        }
    }
}
