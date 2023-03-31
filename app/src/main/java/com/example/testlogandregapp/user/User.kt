package com.example.testlogandregapp.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "user_password") val password: String,
    @ColumnInfo(name = "user_phone_number") val phoneNumber: String
)