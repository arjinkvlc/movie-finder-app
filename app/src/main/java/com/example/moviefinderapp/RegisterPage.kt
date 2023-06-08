package com.example.moviefinderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moviefinderapp.databinding.ActivityRegisterPageBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        val database = Firebase.database
        val myRef = database.getReference("users")
        val registerButton = binding.registerButton
        val auth = FirebaseAuth.getInstance()
        registerButton.setOnClickListener {
            val usernameInput = binding.signupUsername
            val emailInput = binding.signupEmail
            val passwordInput = binding.signupPassword
            val username = usernameInput?.text.toString()
            val email = emailInput?.text.toString()
            //Firebase Realtime veritabanina kullanici bilgilerini kaydetme
            val password = passwordInput?.text.toString()
            val userData = User(username, email, password)
            if (username != "" && email != "" && password != "") {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            myRef.child(username).setValue(userData)
                            Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT)
                                .show()

                            val mainPage = Intent(this, HomePage::class.java).apply {
                                putExtra("username", username)
                            }
                            startActivity(mainPage)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Wrong email or password type !",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Please fill all the form!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}

data class User(val username: String, val email: String, val password: String) {

}