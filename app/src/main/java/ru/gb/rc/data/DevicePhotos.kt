package ru.gb.rc.data

import android.health.connect.datatypes.Device

data class DevicePhotos(
    val devices: List<Device>
)

data class Device(
    val id: Int,
    val location: String,
    val img_src: String
)