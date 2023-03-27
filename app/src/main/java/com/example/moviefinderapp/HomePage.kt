package com.example.moviefinderapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.moviefinderapp.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MoviesFragment())
        //Username'i activityden fragment'a gecirme
        val getUsername:String? = intent.getStringExtra("username")
        Toast.makeText(this, getUsername.toString(), Toast.LENGTH_SHORT).show()

        //Veritabanindan username alip fragmentta yazdirma

        //BottomNavigationView'in menu ogelerini olusturmak icin inflateMenu metodu kullanildi
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        //BottomNavigationView'in arkaplan saydamlastirmasi kismi
        val background = ContextCompat.getDrawable(this, R.drawable.transparent)
        findViewById<BottomNavigationView>(R.id.bottom_navigation_bar).background = background
        //NavigationBar menuleri arasinda gecis ve fonksiyonlarini tanimlama
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_movies -> replaceFragment(MoviesFragment())
                R.id.navigation_search -> replaceFragment(SearchFragment())
                R.id.navigation_profile -> replaceFragment(ProfileFragment(getUsername.toString()))
                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val frangmentManager = supportFragmentManager
        val fragmentTransaction = frangmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}