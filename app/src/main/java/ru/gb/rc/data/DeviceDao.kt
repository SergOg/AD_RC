package ru.gb.rc.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Transaction
    @Query("SELECT * FROM device")
    fun getAll(): Flow<List<DeviceWithSettings>>

    @Insert(entity = Device::class)
    suspend fun insert(device: NewDevice)

    @Delete
    suspend fun delete(device: Device)

    @Update
    suspend fun update(device: Device)

    @Query("UPDATE device SET imgSrc = :imgSrc WHERE id = :id")
    suspend fun updateColumn(id: Int, imgSrc: String)

    @Query("SELECT * FROM device WHERE id = :id")
    suspend fun getOne(id: Int) : Device?
}