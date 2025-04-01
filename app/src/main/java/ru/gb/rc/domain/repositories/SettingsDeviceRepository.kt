package ru.gb.rc.domain.repositories

import ru.gb.rc.data.CommandId
import ru.gb.rc.domain.entities.SettingsDeviceEntity

interface SettingsDeviceRepository {
    suspend fun getAllCommands(id: Int): List<SettingsDeviceEntity>
    suspend fun update(device: SettingsDeviceEntity)
    suspend fun deleteByCommandId(deviceId: Int, commandId: CommandId): Int
    suspend fun insert(
        deviceId: Int,
        commandId: String,
        content: String,
    )
    suspend fun deleteById(deviceId: Int): Int
}