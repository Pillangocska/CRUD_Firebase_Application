package com.example.poincareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.poincareapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class LoggedInUser : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //I leave this here in case user id were needed
        //val userId = intent.getStringExtra("user_id")
        //binding.tvUserId.text = "User ID :: $userId"

        val emailId = intent.getStringExtra("email_id")
        binding.textViewEmail.text = emailId

        binding.logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@LoggedInUser,LoginActivity::class.java))
            finish()
        }
    }
}