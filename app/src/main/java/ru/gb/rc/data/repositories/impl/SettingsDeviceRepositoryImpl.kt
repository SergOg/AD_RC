package ru.gb.rc.data.repositories.impl

import ru.gb.rc.data.CommandId
import ru.gb.rc.data.NewSettingsDevice
import ru.gb.rc.data.SettingsDevice
import ru.gb.rc.data.SettingsDeviceDao
import ru.gb.rc.data.repositories.SettingsDeviceRepository

class SettingsDeviceRepositoryImpl(private val settingsDeviceDao: SettingsDeviceDao) :
    SettingsDeviceRepository {
    override suspend fun getAllCommands(id: Int) = settingsDeviceDao.getAllCommands(id)
    override suspend fun update(device: SettingsDevice) = settingsDeviceDao.update(device)
    override suspend fun deleteByCommandId(deviceId: Int, commandId: CommandId)=
        settingsDeviceDao.deleteByCommandId(deviceId, commandId)

    override suspend fun insert(device: NewSettingsDevice) = settingsDeviceDao.insert(device)
}