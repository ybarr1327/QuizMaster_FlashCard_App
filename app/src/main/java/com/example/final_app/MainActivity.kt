package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val FCBtn = findViewById<Button>(R.id.goToEditFCAct)
        val StudyFlashbtn = findViewById<Button>(R.id.goToStudyFCAct)

        FCBtn.setOnClickListener {
            startActivity(Intent(this,SelectGroupAct::class.java))
        }

        StudyFlashbtn.setOnClickListener {
            startActivity(Intent(this, study_flashcards::class.java))
        }



    }
}