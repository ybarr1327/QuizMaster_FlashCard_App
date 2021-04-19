package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FlashcardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)
        
        
        val FCBtn = findViewById<Button>(R.id.goToEditFCAct)

        FCBtn.setOnClickListener {
            startActivity(Intent(this,SelectGroupAct::class.java))
        }
        
        
        
        
    }
}