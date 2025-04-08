package ru.gb.rc.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.gb.rc.data.AppDatabase
import ru.gb.rc.data.DeviceDao
import ru.gb.rc.data.SettingsDeviceDao

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    fun provideDataBase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }

    @Provides
    fun provideDeviceDao(
        appDatabase: AppDatabase
    ): DeviceDao {
        return appDatabase.deviceDao()
    }

    @Provides
    fun provideSettingsDeviceDao(
        appDatabase: AppDatabase
    ): SettingsDeviceDao {
        return appDatabase.settingsDeviceDao()
    }
}