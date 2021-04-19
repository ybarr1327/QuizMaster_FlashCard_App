package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val gotoFCActBtn = findViewById<Button>(R.id.GoToFCActBtn)



        gotoFCActBtn.setOnClickListener {
            startActivity(Intent(this, FlashcardActivity::class.java))
        }



    }
}