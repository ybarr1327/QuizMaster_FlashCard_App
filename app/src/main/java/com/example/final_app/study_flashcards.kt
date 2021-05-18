package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.example.final_app.dataGroup.GroupViewModel
import androidx.lifecycle.Observer

class study_flashcards : AppCompatActivity() {
    private lateinit var mGroupViewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_flashcards)

        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        val group_spinner = findViewById<Spinner>(R.id.group)
        var group_adapter = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item)
        val sub_group_spinner = findViewById<Spinner>(R.id.subgroup)
        var sub_group_adapter = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item)
        val studyBtn = findViewById<Button>(R.id.Study_button)
        val Backbtn = findViewById<Button>(R.id.Backbutton)

        Backbtn.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        studyBtn.setOnClickListener {
            if(group_spinner.selectedItem != null && group_spinner.count != 0 &&
                sub_group_spinner.selectedItem != null && sub_group_spinner.count != 0)
            {
                val groupName = group_spinner.selectedItem.toString()
                val subGroupName = sub_group_spinner.selectedItem.toString()
                val intent = Intent(this, study::class.java)
                intent.putExtra("group_name", groupName)
                intent.putExtra("sub_group_name", subGroupName)
                startActivity(intent)
            }

        }

        mGroupViewModel.readAllGroups.observe(this, Observer { group ->
            group_adapter.clear()
            group?.forEach {
                group_adapter.add(it.groupName)
            }
        })
        group_spinner.adapter = group_adapter

        mGroupViewModel.returnedSubGroups.observe(this, Observer { subgroup ->
            sub_group_adapter.clear()
            subgroup?.forEach {
                sub_group_adapter.add(it.subGroupName)
            }
        })
        sub_group_spinner.adapter = sub_group_adapter


        group_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                mGroupViewModel.getSubGroupsOfGroup(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


    }

    private fun inputCheck(groupName: String): Boolean {
        return !(TextUtils.isEmpty(groupName))
    }
}