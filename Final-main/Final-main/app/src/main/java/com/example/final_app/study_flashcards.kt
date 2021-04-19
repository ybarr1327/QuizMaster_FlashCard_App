package com.example.final_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner

class study_flashcards : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_flashcards)

        val group_spinner = findViewById<Spinner>(R.id.group)
        val sub_group_spinner = findViewById<Spinner>(R.id.subgroup)



    }
}