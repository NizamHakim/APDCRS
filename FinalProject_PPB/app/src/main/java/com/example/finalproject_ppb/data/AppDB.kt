package com.example.finalproject_ppb.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Cure::class,
        Disease::class,
        Record::class
    ],
    version = 1
)

abstract class AppDB: RoomDatabase() {
    abstract val dao: AppDao
}