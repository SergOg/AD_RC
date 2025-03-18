package ru.gb.rc.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDeviceDao {

    @Insert(entity = SettingsDevice::class)
    suspend fun insert(settingsDevice: SettingsDevice)

//    @Delete
//    suspend fun delete(deviceId: Int)

    @Update
    suspend fun update(settingsDevice: SettingsDevice)

    @Query("SELECT * FROM settings WHERE device_id = :deviceId")
    suspend fun getAllCommands(deviceId: Int) : List<SettingsDevice>
}