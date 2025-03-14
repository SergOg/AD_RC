package ru.gb.rc.data

import androidx.room.Embedded
import androidx.room.Relation

data class DeviceWithSettings(
    @Embedded
    val device: Device,
    @Relation(
        parentColumn = "id",
        entityColumn = "device_id"
    )
    val settingsDevice: SettingsDevice?
)
