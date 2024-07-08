package com.example.moviefinderapp

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moviefinderapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //Firebase Console'un çalıştırılması için başlatma komutu
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        //Butonları ve inputları değişkene atama kısmı
        val loginButton = binding.loginButton
        val emailInput = binding.usernameOrEmailInput
        val passwordInput = binding.passwordInput
        //Oturum açmaya çalışıldığında Authantication kontrolu ve duruma göre oturumun açılması/hata mesajı gönderilmesi
        loginButton.setOnClickListener {
            val email = emailInput?.text.toString()
            val password = passwordInput?.text.toString()
            if (email != "" && password != "") {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Signed Successfully", Toast.LENGTH_SHORT).show()
                            val homePage = Intent(this, HomePage::class.java).apply {
                                putExtra("username", email)
                            }
                            startActivity(homePage)
                            //Oturum acildiktan sonra geri dönüldüğünde uygulamadan cikis yapimasini sağlar
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Wrong username/email or password",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "username/email or password cannot be empty!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        //Kayıt olmak isteyen kullanıcılar butona bastığında Register Page'a geçiş yapılması komutu
        val registerPage = Intent(this, RegisterPage::class.java)
        val signupButton = binding.signupButton
        signupButton.paintFlags = signupButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        signupButton.setOnClickListener {
            startActivity(registerPage)
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null) {
            startActivity(Intent(this@MainActivity, HomePage::class.java))


        }
    }
}