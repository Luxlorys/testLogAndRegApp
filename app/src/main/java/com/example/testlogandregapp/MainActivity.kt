package com.example.testlogandregapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testlogandregapp.databinding.ActivityMainBinding
import com.example.testlogandregapp.db.AppUserDatabase
import com.example.testlogandregapp.user.User
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDb: AppUserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppUserDatabase.getDatabase(this)

        binding.regBtn.setOnClickListener{
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        binding.logInBtn.setOnClickListener { login() }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun login() {
        val name = binding.username.text.toString()
        val password = binding.userPassword.text.toString()

        if (name.isNotEmpty() && password.isNotEmpty()) {
            GlobalScope.launch {
                val user: User? = appDb.studentDao().findUserByUsernameAndPassword(name, password)

                withContext(Dispatchers.Main) {
                    if (user != null) {

                        val intent = Intent(applicationContext, UserActivity::class.java)
                        intent.putExtra("name", name)

                        binding.logInBtn.setOnClickListener {
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}