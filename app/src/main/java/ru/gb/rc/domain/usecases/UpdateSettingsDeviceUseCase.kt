package ru.gb.rc.domain.usecases

import ru.gb.rc.data.repositories.impl.SettingsDeviceRepositoryImpl
import ru.gb.rc.domain.entities.SettingsDeviceEntity
import javax.inject.Inject

class UpdateSettingsDeviceUseCase @Inject constructor(private val repository: SettingsDeviceRepositoryImpl) {
    suspend operator fun invoke(settingsDeviceEntity: SettingsDeviceEntity) =
        repository.update(settingsDeviceEntity)
}