package ru.gb.rc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeviceDao {

    @Query("SELECT * FROM device")
    fun getAll(): List<Device>

    @Insert
    fun insert(device: Device)

    @Delete
    fun delete(device: Device)

    @Update
    fun update(device: Device)
}