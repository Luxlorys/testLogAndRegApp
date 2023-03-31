package com.example.testlogandregapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testlogandregapp.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.username.text = intent.getStringExtra("name")
    }
}