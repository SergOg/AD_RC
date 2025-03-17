package ru.gb.rc.domain

data class DeviceSettingsViewState(
    val id: Int? = null,
    val location: String = "",
    val imgSrc: String = "",
    val protocol: String = "",
    val equipment: String = "",
)