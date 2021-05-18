package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button used for launching the edit flash card activity
        val FCBtn = findViewById<Button>(R.id.goToEditFCAct)
        // button used to launch the study flashcard activity
        val StudyFlashbtn = findViewById<Button>(R.id.goToStudyFCAct)

        // listens for the user to click the edit flashcard button then launches that activity.
        FCBtn.setOnClickListener {
            startActivity(Intent(this,SelectGroupAct::class.java))
        }
        // listens for the user to click the study flashcard button then launches that activity.
        StudyFlashbtn.setOnClickListener {
            startActivity(Intent(this, study_flashcards::class.java))
        }



    }
}