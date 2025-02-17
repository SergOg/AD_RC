package ru.gb.rc.presentation.ble

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BleViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is BLE Fragment"
    }
    val text: LiveData<String> = _text
}