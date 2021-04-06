package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Edit_FC_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__f_c_)

        val editConfBtn = findViewById<Button>(R.id.EditConfirmBtn)

        editConfBtn.setOnClickListener {
            startActivity(Intent(this, EditFlashCardsAct::class.java))
        }
    }
}