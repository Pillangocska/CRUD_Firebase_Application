package com.example.poincareapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.poincareapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener{
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            logInTheUser()
        }
    }

    private fun logInTheUser(){
        when {
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

                FirebaseAuth.getInstance().signInWithEmailAndPassword(fireEmail,firePassword)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->
                            //If the registration is complete
                            if (task.isSuccessful){
                                //Firebase user
                                //task.result!!.user!!
                                Snackbar.make(binding.root,"User logged in", Snackbar.LENGTH_SHORT).show()

                                //Redirect the user to the main screen
                                val intent = Intent(this, LoggedInUser::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", fireEmail)
                                startActivity(intent)
                            }
                            else {
                                Snackbar.make(binding.root,task.exception!!.message.toString(),
                                    Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    )
            }
        }
    }
}