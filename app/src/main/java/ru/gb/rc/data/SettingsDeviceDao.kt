package ru.gb.rc.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDeviceDao {

    @Transaction
    @Query("SELECT * FROM device")
    fun getAll(): Flow<List<SettingsDevice>>

    @Insert(entity = SettingsDevice::class)
    suspend fun insert(settingsDevice: SettingsDevice)

    @Delete
    suspend fun delete(settingsDevice: SettingsDevice)

    @Update
    suspend fun update(settingsDevice: SettingsDevice)

    @Query("SELECT * FROM device WHERE devoiceId = :deviceId")
    suspend fun getOne(deviceId: Int) : SettingsDevice?
}