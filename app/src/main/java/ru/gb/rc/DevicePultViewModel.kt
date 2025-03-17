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
import kotlinx.coroutines.launch
import ru.gb.rc.data.DeviceDao

@HiltViewModel(assistedFactory = DevicePultViewModel.Factory::class)
class DevicePultViewModel @AssistedInject constructor(
    private val deviceDao: DeviceDao,
    @Assisted val id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DevicePultViewModel
    }

    private val _state = MutableLiveData<DevicePultViewState>(DevicePultViewState())
    val state: LiveData<DevicePultViewState> = _state

    init {
        init(id)
    }

    fun init(id: Int) {
        Log.d("DevicePultViewModel", id.toString())
        viewModelScope.launch {
            val device = deviceDao.getOne(id)
            device?.let {
                _state.value = DevicePultViewState(
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
