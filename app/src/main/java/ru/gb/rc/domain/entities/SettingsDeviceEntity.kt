package ru.gb.rc.domain.entities

data class SettingsDeviceEntity(
    val id: Int,
    val deviceId: Int,
    val commandId: String,
    val content: String,
)
