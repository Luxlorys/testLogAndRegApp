package com.example.testlogandregapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testlogandregapp.databinding.ActivityRegisterBinding
import com.example.testlogandregapp.db.AppUserDatabase
import com.example.testlogandregapp.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var appDb: AppUserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppUserDatabase.getDatabase(this)


        binding.regBtn.setOnClickListener{
            lifecycleScope.launch {
                if (checkNumberInDB() && checkUserInDB()) registerNewUser()
                Toast.makeText(this@RegisterActivity, "User is already exists", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logInBtn.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    private suspend fun registerNewUser(){
        val username = binding.username.text.toString()
        val password = binding.userPassword.text.toString()
        val phoneNumber = binding.userPhoneNumber.text.toString()

        if(username.isNotEmpty()
            && password.isNotEmpty()
            && phoneNumber.isNotEmpty()) {

            withContext(Dispatchers.IO) {
                appDb.studentDao().insert(User(null, username, password, phoneNumber))
            }

            Toast.makeText(this@RegisterActivity, "Successfully written", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity::class.java))
        } else {
            Toast.makeText(this@RegisterActivity, "Please, enter data", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun checkNumberInDB(): Boolean {
        val userPhoneNumber = binding.userPhoneNumber.text.toString()

        if (binding.username.text.toString().isNotEmpty()
            && binding.userPassword.text.toString().isNotEmpty()
            && userPhoneNumber.isNotEmpty()) {

            val user: User? = withContext(Dispatchers.IO) {
                appDb.studentDao().phoneAlreadyExists(userPhoneNumber)
            }

            return user == null
        }

        return false
    }

    private suspend fun checkUserInDB(): Boolean {
        val username = binding.username.text.toString()

        if (binding.userPhoneNumber.text.toString().isNotEmpty()
            && binding.userPassword.text.toString().isNotEmpty()
            && username.isNotEmpty()) {

            val user: User? = withContext(Dispatchers.IO) {
                appDb.studentDao().usernameAlreadyExists(username)
            }

            return user == null
        }

        return false
    }
}