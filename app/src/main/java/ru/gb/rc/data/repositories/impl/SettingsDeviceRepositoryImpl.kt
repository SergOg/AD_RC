package ru.gb.rc.data.repositories.impl

import ru.gb.rc.data.CommandId
import ru.gb.rc.data.NewSettingsDevice
import ru.gb.rc.data.SettingsDeviceDao
import ru.gb.rc.data.mappers.SettingsDeviceMapper
import ru.gb.rc.domain.entities.SettingsDeviceEntity
import ru.gb.rc.domain.repositories.SettingsDeviceRepository
import javax.inject.Inject

class SettingsDeviceRepositoryImpl @Inject constructor(private val settingsDeviceDao: SettingsDeviceDao) :
    SettingsDeviceRepository {
    override suspend fun getAllCommands(id: Int) = settingsDeviceDao.getAllCommands(id).map {
        SettingsDeviceMapper.toDomain(it)
    }

    override suspend fun update(settingsDevice: SettingsDeviceEntity) =
        settingsDeviceDao.update(
            SettingsDeviceMapper.toData(
                settingsDevice
            )
        )

    override suspend fun deleteByCommandId(deviceId: Int, commandId: CommandId) =
        settingsDeviceDao.deleteByCommandId(deviceId, commandId)

    override suspend fun deleteById(deviceId: Int) = settingsDeviceDao.deleteById(deviceId)

    override suspend fun insert(
        deviceId: Int,
        commandId: String,
        content: String,
    ) =
        settingsDeviceDao.insert(
            NewSettingsDevice(
                deviceId = deviceId,
                commandId = commandId,
                content = content,
            )
        )
}