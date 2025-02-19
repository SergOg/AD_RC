package ru.gb.rc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM device")
    fun getAll(): Flow<List<Device>>

    @Insert(entity = Device::class)
    suspend fun insert(device: NewDevice)

    @Delete
    suspend fun delete(device: Device)

    @Update
    suspend fun update(device: Device)
}