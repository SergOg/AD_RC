package ru.gb.rc.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.gb.rc.data.Device

interface DeviceRepository {
    suspend fun getAll(): Flow<List<Device>>
    suspend fun delete(device: Device)
}