package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner

class study_flashcards : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_flashcards)

        val group_spinner = findViewById<Spinner>(R.id.group)
        val sub_group_spinner = findViewById<Spinner>(R.id.subgroup)
        val studyBtn = findViewById<Button>(R.id.Study_button)
        val Backbtn = findViewById<Button>(R.id.Backbutton)

        Backbtn.setOnClickListener {
            startActivity(Intent(this,FlashcardActivity::class.java))
        }

        studyBtn.setOnClickListener {
            startActivity(Intent(this,study::class.java))
        }



    }
}