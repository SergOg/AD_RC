package ru.gb.rc.presentation.wifi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WifiViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Wi-Fi Fragment"
    }
    val text: LiveData<String> = _text
}