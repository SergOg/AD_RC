package ru.gb.rc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsDevice(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "device_id") val deviceId: Int,
    @ColumnInfo(name = "power_button") val powerButton: String,
    @ColumnInfo(name = "mute_button") val muteButton: String,
    @ColumnInfo(name = "one_button") val oneButton: String,
    @ColumnInfo(name = "two_button") val twoButton: String,
    @ColumnInfo(name = "three_button") val threeButton: String,
    @ColumnInfo(name = "four_button") val fourButton: String,
    @ColumnInfo(name = "up_button") val upButton: String,
    @ColumnInfo(name = "down_button") val downButton: String,
    @ColumnInfo(name = "minus_button") val minusButton: String,
    @ColumnInfo(name = "plus_button") val plusButton: String,
    )
