package ru.gb.rc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device")
data class Device(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "imgSrc") val imgSrc: String,
    @ColumnInfo(name = "protocol") val protocol: String,
    @ColumnInfo(name = "equipment") val equipment: String,
)