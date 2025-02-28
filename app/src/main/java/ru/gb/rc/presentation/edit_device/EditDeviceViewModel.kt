package ru.gb.rc.presentation.edit_device

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.data.NewDevice
import javax.inject.Inject

@HiltViewModel(assistedFactory = EditDeviceViewModel.Factory::class)
class EditDeviceViewModel @AssistedInject constructor(private val deviceDao: DeviceDao, @Assisted val id: Int) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): EditDeviceViewModel
    }

    private val _state = MutableLiveData<EditDeviceViewState>(EditDeviceViewState())

    private val state: LiveData<EditDeviceViewState> = _state

    fun onAddBtn() {
        viewModelScope.launch {
            deviceDao.insert(
                NewDevice(
                    location = state.value.location,
                    imgSrc = "",
                    protocol = state.value.protocol,
                    device = state.value.device
                )
            )
        }
    }
}