package ru.gb.rc.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class NewDevice(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "imgSrc") val imgSrc: String,
    @ColumnInfo(name = "protocol") val protocol: String,
    @ColumnInfo(name = "equipment") val equipment: String,
)