package ru.gb.rc.domain.usecases

import ru.gb.rc.data.CommandId
import ru.gb.rc.data.repositories.impl.SettingsDeviceRepositoryImpl
import javax.inject.Inject

class DeleteByCommandIdUseCase @Inject constructor(private val repository: SettingsDeviceRepositoryImpl) {
    suspend operator fun invoke(id: Int, commandId: CommandId) =
        repository.deleteByCommandId(id, commandId)
}