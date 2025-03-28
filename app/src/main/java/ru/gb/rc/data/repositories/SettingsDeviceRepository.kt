package ru.gb.rc.data.repositories

interface SettingsDeviceRepository {
    suspend fun deleteById(id: Int)
}