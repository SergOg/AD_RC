package ru.gb.rc.data

import androidx.room.*

@Dao
interface SettingsDeviceDao {

    @Insert(entity = SettingsDevice::class)
    suspend fun insert(newSettingsDevice: NewSettingsDevice)

    @Query("DELETE FROM settings WHERE device_id = :deviceId")
    suspend fun deleteById(deviceId: Int): Int

    @Update
    suspend fun update(settingsDevice: SettingsDevice)


    @Query("SELECT * FROM settings WHERE device_id = :deviceId")
    suspend fun getAllCommands(deviceId: Int) : List<SettingsDevice>
}