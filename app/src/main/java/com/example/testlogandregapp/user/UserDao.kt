package com.example.testlogandregapp.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE username = :username AND user_password = :password")
    fun findUserByUsernameAndPassword(username: String, password: String): User?

    @Query("SELECT * FROM user_table WHERE user_phone_number = :phone")
    fun phoneAlreadyExists(phone: String): User?

    @Query("SELECT * FROM user_table WHERE username = :username")
    fun usernameAlreadyExists(username: String): User?
}