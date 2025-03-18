package ru.gb.rc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsDevice(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "device_id") val deviceId: Int,
    @ColumnInfo(name = "command_id") val commandId: String,
    @ColumnInfo(name = "content") val content: String,
    )
