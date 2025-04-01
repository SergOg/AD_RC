package ru.gb.rc.data.repositories.impl

import ru.gb.rc.data.CommandId
import ru.gb.rc.data.NewSettingsDevice
import ru.gb.rc.data.SettingsDevice
import ru.gb.rc.data.SettingsDeviceDao
import ru.gb.rc.data.repositories.SettingsDeviceRepository
import javax.inject.Inject

class SettingsDeviceRepositoryImpl @Inject constructor(private val settingsDeviceDao: SettingsDeviceDao) :
    SettingsDeviceRepository {
    override suspend fun getAllCommands(id: Int) = settingsDeviceDao.getAllCommands(id)
    override suspend fun update(settingsDevice: SettingsDevice) =
        settingsDeviceDao.update(settingsDevice)

    override suspend fun deleteByCommandId(deviceId: Int, commandId: CommandId) =
        settingsDeviceDao.deleteByCommandId(deviceId, commandId)

    override suspend fun deleteById(deviceId: Int) = settingsDeviceDao.deleteById(deviceId)
    override suspend fun insert(newSettingsDevice: NewSettingsDevice) =
        settingsDeviceDao.insert(newSettingsDevice)
}