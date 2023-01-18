package com.example.poincareapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.poincareapp.databinding.ActivityRegisterUserBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterUser : AppCompatActivity() {
    lateinit var binding: ActivityRegisterUserBinding
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.banner.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.backButton.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerUser.setOnClickListener {
            registerTheUser()
        }
    }

    private fun registerTheUser(){
        when {
            TextUtils.isEmpty(binding.fullName.text.toString().trim() { it <= ' '}) -> {
                binding.fullName.error = "Full name is required"
                binding.fullName.requestFocus()
            }

            TextUtils.isEmpty(binding.email.text.toString().trim() { it <= ' '}) -> {
                binding.email.error = "Email is required"
                binding.email.requestFocus()
            }

            TextUtils.isEmpty(binding.password.text.toString().trim() { it <= ' '}) -> {
                binding.password.error = "Password is required"
                binding.password.requestFocus()
            }

            else -> {
                val fireEmail: String = binding.email.text.toString().trim() { it <= ' '}
                val firePassword: String = binding.password.text.toString().trim() { it <= ' '}

                binding.progressBar.visibility = View.VISIBLE
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(fireEmail,firePassword)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->
                            //If the registration is complete
                            if (task.isSuccessful){
                                //Firebase user
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Snackbar.make(binding.root,"User registered",Snackbar.LENGTH_SHORT).show()
                                binding.progressBar.visibility = View.GONE
                                //Redirect the user to the main screen
                                val intent = Intent(this, LoggedInUser::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", fireEmail)
                                startActivity(intent)
                            }
                            else {
                                Snackbar.make(binding.root,task.exception!!.message.toString(),Snackbar.LENGTH_SHORT).show()
                                binding.progressBar.visibility = View.INVISIBLE
                            }
                        }
                    )
            }
        }
    }
}