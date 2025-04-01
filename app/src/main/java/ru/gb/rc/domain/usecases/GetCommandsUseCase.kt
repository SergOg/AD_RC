package ru.gb.rc.domain.usecases

import ru.gb.rc.data.repositories.impl.SettingsDeviceRepositoryImpl
import javax.inject.Inject

class GetCommandsUseCase @Inject constructor(private val repository: SettingsDeviceRepositoryImpl) {
    suspend operator fun invoke(id: Int) = repository.getAllCommands(id)
}