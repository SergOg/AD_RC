package ru.gb.rc

import android.app.Application
import androidx.room.Room
import ru.gb.rc.data.AppDatabase

class App: Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}