package com.example.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.final_app.dataGroup.GroupViewModel
import androidx.lifecycle.Observer


class study_flashcards : AppCompatActivity() {
    private lateinit var mGroupViewModel: GroupViewModel // this is to interact with the database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_flashcards)

        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java) // this gets the viewmodel of the database

        val group_spinner = findViewById<Spinner>(R.id.group)
        var group_adapter = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item)
        val sub_group_spinner = findViewById<Spinner>(R.id.subgroup)
        var sub_group_adapter = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item)
        val studyBtn = findViewById<Button>(R.id.Study_button)
        val Backbtn = findViewById<Button>(R.id.Backbutton)
        var set_size = 0;
        var groupName:String
        var subGroupName:String

        Backbtn.setOnClickListener {
            finish() // this button closes the activity and goes to the home page
            startActivity(Intent(this, MainActivity::class.java))
        }

        studyBtn.setOnClickListener { // this will open a flashcard set to study in the next activity

            //if the spinners have data and items are selected
            if(group_spinner.selectedItem != null && group_spinner.count != 0 &&
                sub_group_spinner.selectedItem != null && sub_group_spinner.count != 0)
            {
                //get the names
                groupName = group_spinner.selectedItem.toString()
                subGroupName = sub_group_spinner.selectedItem.toString()

                if (set_size >0)//if there are items in the set then we can study
                {
                    set_size = 0 // reset this for the next time
                    //send the names throught the intent and start the study activity
                    val intent = Intent(this, study::class.java)
                    intent.putExtra("group_name", groupName)
                    intent.putExtra("sub_group_name", subGroupName)
                    startActivity(intent)
                }
                else
                {
                    set_size = 0 //reset this for the next time
                    Toast.makeText( //show an error message that there were no flashcards to study from
                        this,
                        "There are no flashcards in this set, please create a flashcard first",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            else
            {
                Toast.makeText( // if here a group or a subgroup was not selected in the first place
                    this,
                    "Please Select a Subject and Cardset",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


        //if the flashcards live data changes, get the size of the set
        mGroupViewModel.returnedFlashCards.observe(this, { Flashcard ->
            if (Flashcard.isNotEmpty()) {
                set_size = Flashcard.size
            }
        })

        //this observes changes to the group names and updates this data to the group spinner adapter
        mGroupViewModel.readAllGroups.observe(this, Observer { group ->
            group_adapter.clear()
            group?.forEach {
                group_adapter.add(it.groupName)
            }
        })
        group_spinner.adapter = group_adapter

        //this observes changes to the sub group items and updates this data to the subgroup adapter
        mGroupViewModel.returnedSubGroups.observe(this, Observer { subgroup ->
            sub_group_adapter.clear()
            subgroup?.forEach {
                sub_group_adapter.add(it.subGroupName)
            }
        })
        sub_group_spinner.adapter = sub_group_adapter


        group_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { //if a group is selected, then the subgroup spinner should also be updated
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                mGroupViewModel.getSubGroupsOfGroup(parent.getItemAtPosition(position).toString()) // update the subgroup spinner with the group's subgroups
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        sub_group_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{ // if a subgroup is selected
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(group_spinner.selectedItem != null && group_spinner.count != 0 &&
                    sub_group_spinner.selectedItem != null && sub_group_spinner.count != 0)
                    { // if there is a valid subgroup and group
                        //get the flashcards of the subgroup
                            //this is basically a utility to check if the set had flashcards
                    groupName = group_spinner.selectedItem.toString()
                    subGroupName = sub_group_spinner.selectedItem.toString()
                    mGroupViewModel.getFlashcards(subGroupName)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }



    }
    //this function checks if the input is valid
    private fun inputCheck(groupName: String): Boolean {
        return !(TextUtils.isEmpty(groupName))
    }
}