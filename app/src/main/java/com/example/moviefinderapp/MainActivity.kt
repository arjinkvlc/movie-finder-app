package com.example.moviefinderapp

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Firebase Console'un çalıştırılması için başlatma komutu
        FirebaseApp.initializeApp(this)
        val auth=FirebaseAuth.getInstance()
        //Butonları ve inputları değişkene atama kısmı
        val loginButton=findViewById<Button>(R.id.login_button)
        val emailInput=findViewById<TextInputEditText>(R.id.username_or_email_input)
        val passwordInput=findViewById<TextInputEditText>(R.id.password_input)
        //Oturum açmaya çalışıldığında Authantication kontrolu ve duruma göre oturumun açılması/hata mesajı gönderilmesi
        loginButton.setOnClickListener{
            val email=emailInput?.text.toString()
            val password=passwordInput?.text.toString()
            if(email!=""&&password!=""){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Signed Successfully", Toast.LENGTH_SHORT).show()
                    val homePage = Intent(this, HomePage::class.java).apply {
                        putExtra("username", email)
                    }

                    startActivity(homePage)
                    //Oturum acildiktan sonra geri dönüldüğünde uygulamadan cikis yapimasini sağlar
                    finish()
                } else {
                    Toast.makeText(this, "Wrong username/email or password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            }else{
                Toast.makeText(this, "username/email or password cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        //Kayıt olmak isteyen kullanıcılar butona bastığında Register Page'a geçiş yapılması komutu
        val registerPage=Intent(this,RegisterPage::class.java)
        val signupButton=findViewById<Button>(R.id.signup_button)
        signupButton.paintFlags=signupButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        signupButton.setOnClickListener{
            startActivity(registerPage)
        }
    }
}