package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class SelectGroupAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectgroup)

        val editConfBtn = findViewById<Button>(R.id.EditConfirmBtn)
        val editGroupBtn = findViewById<Button>(R.id.GoToEdit_AddBtn)




        editConfBtn.setOnClickListener {
            startActivity(Intent(this, EditFlashCardsAct::class.java))
        }

        editGroupBtn.setOnClickListener {
            startActivity(Intent(this,EditAddRemoveGroups::class.java))
        }

    }
}