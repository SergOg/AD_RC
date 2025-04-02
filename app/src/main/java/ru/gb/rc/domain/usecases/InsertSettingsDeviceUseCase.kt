package ru.gb.rc.domain.usecases

import ru.gb.rc.data.CommandId
import ru.gb.rc.data.repositories.impl.SettingsDeviceRepositoryImpl
import ru.gb.rc.domain.entities.SettingsDeviceEntity
import javax.inject.Inject

class InsertSettingsDeviceUseCase @Inject constructor(private val repository: SettingsDeviceRepositoryImpl) {
    suspend operator fun invoke(id: Int, commandId: String, content: String) =
        repository.insert(id, commandId, content)
}