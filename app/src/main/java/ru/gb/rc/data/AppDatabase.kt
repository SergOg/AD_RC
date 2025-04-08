package ru.gb.rc.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Device::class,
        SettingsDevice::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
    abstract fun settingsDeviceDao(): SettingsDeviceDao
}