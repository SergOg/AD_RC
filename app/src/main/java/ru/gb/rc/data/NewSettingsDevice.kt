package ru.gb.rc.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class NewSettingsDevice(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "device_id") val deviceId: Int,
    @ColumnInfo(name = "command_id") val commandId: String,
    @ColumnInfo(name = "content") val content: String,
    )
