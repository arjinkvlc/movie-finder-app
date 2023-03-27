package com.example.moviefinderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        FirebaseApp.initializeApp(this)
        val database = Firebase.database
        val myRef = database.getReference("users")
        val registerButton = findViewById<Button>(R.id.register_button)
        val auth = FirebaseAuth.getInstance()
        registerButton.setOnClickListener {
            val usernameInput = findViewById<TextInputEditText>(R.id.signup_username)
            val emailInput = findViewById<TextInputEditText>(R.id.signup_email)
            val passwordInput = findViewById<TextInputEditText>(R.id.signup_password)
            val username = usernameInput?.text.toString()
            val email = emailInput?.text.toString()
            //Firebase Realtime veritabanina kullanici bilgilerini kaydetme
            val password = passwordInput?.text.toString()
            val userData = User(username, email, password)
            myRef.child(username).setValue(userData)

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()

                        val mainPage = Intent(this, HomePage::class.java).apply {
                            putExtra("username", username)
                        }
                        startActivity(mainPage)
                        finish()
                    } else {
                        Toast.makeText(this, "Wrong email or password type !", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}

class User(val username: String, val email: String, val password: String){

}