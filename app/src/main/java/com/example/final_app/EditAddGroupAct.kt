package com.example.final_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditAddGroupAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_group)

        val addBtn = findViewById<Button>(R.id.AddBtn)
        val removeBtn = findViewById<Button>(R.id.removeBtn)

        val addGroupBtn = findViewById<Button>(R.id.AddGroup)
        val addSubGroupBtn = findViewById<Button>(R.id.AddSubGroup)

        val deleteGroupBtn = findViewById<Button>(R.id.DeleteGroupBtn)
        val deleteSubGroupBtn = findViewById<Button>(R.id.DeleteSubGroupBtn)

        val enterGroupLabel = findViewById<TextView>(R.id.EnterGroupLabel)
        val enterGroupName = findViewById<EditText>(R.id.EnterGroupName)

        val enterSubGroupLabel = findViewById<TextView>(R.id.EnterSubGroupLabel)
        val enterSubGroupName = findViewById<EditText>(R.id.EnterSubGroupName)

        val groupSpinner = findViewById<Spinner>(R.id.GroupSpinner)
        val subGroupSpinner = findViewById<Spinner>(R.id.SubGroupSpinner)

        val selectGroupLabel = findViewById<TextView>(R.id.SelectGroupLabel)
        val selectSubGroupLabel = findViewById<TextView>(R.id.SelectSubGroupLabel)

        val confirmBtn = findViewById<Button>(R.id.ConfirmButton)
        val cancelBtn = findViewById<Button>(R.id.CancelGroupEditBtn)

        addBtn.setOnClickListener {
            toggleViewVisibility(addGroupBtn)
            toggleViewVisibility(addSubGroupBtn)
            toggleViewVisibility(cancelBtn)
            toggleViewVisibility(addBtn)
            toggleViewVisibility(removeBtn)
        }

        removeBtn.setOnClickListener {
            toggleViewVisibility(deleteGroupBtn)
            toggleViewVisibility(deleteSubGroupBtn)
            toggleViewVisibility(cancelBtn)
            toggleViewVisibility(addBtn)
            toggleViewVisibility(removeBtn)
        }

        cancelBtn.setOnClickListener {
            finish();
            startActivity(Intent(this,SelectGroupAct::class.java))

        }

        confirmBtn.setOnClickListener {
            finish();
            startActivity(Intent(this,EditAddGroupAct::class.java))
        }

        addGroupBtn.setOnClickListener {
            toggleViewVisibility(enterGroupLabel)
            toggleViewVisibility(enterGroupName)
            toggleViewVisibility(confirmBtn)
            toggleViewVisibility(addGroupBtn)
            toggleViewVisibility(addSubGroupBtn)
        }

        addSubGroupBtn.setOnClickListener {
            toggleViewVisibility(selectGroupLabel)
            toggleViewVisibility(groupSpinner)

            toggleViewVisibility(enterSubGroupLabel)
            toggleViewVisibility(enterSubGroupName)
            toggleViewVisibility(confirmBtn)
            toggleViewVisibility(addGroupBtn)
            toggleViewVisibility(addSubGroupBtn)
        }

        deleteGroupBtn.setOnClickListener {
            toggleViewVisibility(selectGroupLabel)
            toggleViewVisibility(groupSpinner)
//            toggleViewVisibility(selectSubGroupLabel)
//            toggleViewVisibility(subGroupSpinner)
            toggleViewVisibility(confirmBtn)
            toggleViewVisibility(deleteGroupBtn)
            toggleViewVisibility(deleteSubGroupBtn)
        }

        deleteSubGroupBtn.setOnClickListener {
            toggleViewVisibility(selectGroupLabel)
            toggleViewVisibility(groupSpinner)
            toggleViewVisibility(selectSubGroupLabel)
            toggleViewVisibility(subGroupSpinner)
            toggleViewVisibility(confirmBtn)
            toggleViewVisibility(deleteGroupBtn)
            toggleViewVisibility(deleteSubGroupBtn)
        }


    }
}

fun toggleViewVisibility(view: View) {
    if (view.visibility == View.INVISIBLE)
        view.visibility = View.VISIBLE
    else if (view.visibility == View.VISIBLE)
        view.visibility = View.INVISIBLE
}