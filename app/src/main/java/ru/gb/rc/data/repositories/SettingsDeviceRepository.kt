package ru.gb.rc.data.repositories

import ru.gb.rc.data.CommandId
import ru.gb.rc.data.NewSettingsDevice
import ru.gb.rc.data.SettingsDevice

interface SettingsDeviceRepository {
    suspend fun getAllCommands(id: Int): List<SettingsDevice>
    suspend fun update(device: SettingsDevice)
    suspend fun deleteByCommandId(deviceId: Int, commandId: CommandId): Int
    suspend fun insert(device: NewSettingsDevice)
    suspend fun deleteById(deviceId: Int): Int
}