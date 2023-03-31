package com.example.testlogandregapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testlogandregapp.user.User
import com.example.testlogandregapp.user.UserDao

@Database(entities = [User::class], version = 2)
abstract class AppUserDatabase : RoomDatabase() {
    abstract fun studentDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppUserDatabase? = null

        fun getDatabase(context: Context): AppUserDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppUserDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}