package ru.gb.rc.data.usecases

import ru.gb.rc.data.Device
import ru.gb.rc.data.repositories.DeviceRepository
import ru.gb.rc.data.repositories.SettingsDeviceRepository
import ru.gb.rc.domain.DeleteDeviceUseCase

class DeleteDeviceUseCaseImpl(
    private val deviceRepository: DeviceRepository,
    private val settingsDeviceRepository: SettingsDeviceRepository
) : DeleteDeviceUseCase {

    override suspend fun invoke(device: Device) {
        deviceRepository.delete(device)
        settingsDeviceRepository.deleteById(device.id)
    }
}