package com.example.moviefinderapp
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class ProfileFragment (val username:String): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        val text:TextView=view.findViewById(R.id.username_onProfile)
        //val value=arguments?.getString("username")
        text.setText(username)
        val logoutButton=view.findViewById<ImageButton>(R.id.logout_button)
        logoutButton.setOnClickListener(){
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(activity,"Log out Successfully!",Toast.LENGTH_SHORT).show()
        }
        return view



    }
}