package ru.gb.rc.data.mappers

import ru.gb.rc.data.SettingsDevice
import ru.gb.rc.domain.entities.SettingsDeviceEntity

object SettingsDeviceMapper {
    fun toDomain(data: SettingsDevice): SettingsDeviceEntity = SettingsDeviceEntity(
        id = data.id,
        deviceId = data.deviceId,
        commandId = data.commandId,
        content = data.content
    )

    fun toData(entity: SettingsDeviceEntity): SettingsDevice = SettingsDevice(
        id = entity.id,
        deviceId = entity.deviceId,
        commandId = entity.commandId,
        content = entity.content
    )
}