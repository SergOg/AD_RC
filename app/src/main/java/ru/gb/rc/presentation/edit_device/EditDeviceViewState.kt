package ru.gb.rc.presentation.edit_device

data class EditDeviceViewState(
    val id: Int? = null,
    val location: String = "",
    val imgSrc: String = "",
    val protocol: String = "",
    val device: String = "",
)