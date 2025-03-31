package ru.gb.rc.domain

import ru.gb.rc.data.repositories.SettingsDeviceRepository

class GetCommandsUseCase(private val repository: SettingsDeviceRepository) {
    suspend operator fun invoke(id: Int) = repository.getAllCommands(id)
}