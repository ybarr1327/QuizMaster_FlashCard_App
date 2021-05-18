package com.example.final_app

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.final_app.dataGroup.Group
import com.example.final_app.dataGroup.GroupViewModel
import com.example.final_app.dataGroup.SubGroup


class SelectGroupAct : AppCompatActivity() {
    private lateinit var mGroupViewModel: GroupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectgroup)

        //this gets the viewmodel of the database to access it
        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        val editConfBtn = findViewById<Button>(R.id.EditConfirmBtn)

        val addGroupBtn: View = findViewById(R.id.add_group_button)
        val delGroupBtn: View = findViewById(R.id.delete_group_button)
        val editGroupBtn: View = findViewById(R.id.edit_group_button)

        val addSubGroupBtn: View = findViewById(R.id.add_sub_group_button)
        val delSubGroupBtn: View = findViewById(R.id.del_sub_group_button)
        val editSubGroupBtn: View = findViewById(R.id.edit_sub_group_button)

        val group_spinner = findViewById<Spinner>(R.id.GroupSpinner)
        var group_adapter = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item)

        val sub_group_spinner = findViewById<Spinner>(R.id.SubGroupSpinner)
        var sub_group_adapter = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item)

        //update the adapter with the groups of the database
        mGroupViewModel.readAllGroups.observe(this, Observer { group ->
            group_adapter.clear() // clear all
            group?.forEach { // re add the items by iteration
                group_adapter.add(it.groupName)
            }
        })
        group_spinner.adapter = group_adapter

        //update the adapter with the subgroups of the database
        mGroupViewModel.returnedSubGroups.observe(this, Observer { subgroup ->
            sub_group_adapter.clear()
            subgroup?.forEach {
                sub_group_adapter.add(it.subGroupName)
            }
        })
        sub_group_spinner.adapter = sub_group_adapter

        //if an item in the group spinner is selected, then the subgroup's data needs to be updated
        group_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                mGroupViewModel.getSubGroupsOfGroup(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        addGroupBtn.setOnClickListener {
            //build the dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add a new group?")
            builder.setMessage("Enter The Group Name: ")

            //declare the input and make it part of the dialog
            val input = EditText(this)
            input.hint = "Name"
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)


            builder.setPositiveButton("Add") { _, _ ->
                val NewGroupName = input.text.toString() // get the input text
                if (inputCheck(NewGroupName)) {
                    //create user object
                    val group = Group(NewGroupName) //create the group with the input's text
                    //add data to database
                    mGroupViewModel.addGroup(group)
                    Toast.makeText(this, "Successfully added $NewGroupName", Toast.LENGTH_LONG)
                        .show()

                    if (group_spinner.count != 0) { //after adding the group, update the subgroups spinner with the new data
                        val newItemSelected: String = group_spinner.selectedItem as String
                        mGroupViewModel.getSubGroupsOfGroup(newItemSelected)
                    }
                } else { // if the input is not correct show an error message
                    Toast.makeText(
                        this,
                        "Edit Failed, Please fill out name fields",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

            builder.setCancelable(false)

            builder.create().show()


        }

        delGroupBtn.setOnClickListener {
            if (group_spinner.count != 0) { //if a group is selected
                val itemSelected: String = group_spinner.selectedItem as String
                val builder = AlertDialog.Builder(this) // bulild the dialog
                builder.setTitle("Delete $itemSelected?")
                builder.setMessage("Are you sure you want to delete $itemSelected?")


                builder.setPositiveButton("Yes") { _, _ ->

                    mGroupViewModel.deleteGroup(itemSelected) // delete the selected item from the spinner
                    Toast.makeText(
                        this,
                        "Successfully removed $itemSelected",
                        Toast.LENGTH_LONG
                    ).show()
                    if (group_spinner.count != 0) { // update the subgroup spinner
                        val newItemSelected: String = group_spinner.selectedItem as String
                        mGroupViewModel.getSubGroupsOfGroup(newItemSelected)
                    }
                    else
                    {
                        sub_group_adapter.clear()
                    }
                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                builder.setCancelable(false)
                builder.create().show()
            }
        }

        editGroupBtn.setOnClickListener {
            if (group_spinner.count != 0) { // if a group is selected
                val itemSelectedName: String = group_spinner.selectedItem as String

                //create the builder
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Edit ${itemSelectedName}?")
                builder.setMessage("Change the Group Name of ${itemSelectedName}:")

                //create the input
                val input = EditText(this)
                input.hint = "Name"
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton("Yes") { _, _ ->
                    val NewGroupName = input.text.toString()
                    if (inputCheck(NewGroupName)) { //edit the group selected by changing the name to the new text input
                        mGroupViewModel.updateGroup(itemSelectedName, NewGroupName)
                        Toast.makeText(
                            this,
                            "Successfully changed to $NewGroupName",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Add Failed, Please fill out name fields",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                builder.setCancelable(false)
                builder.create().show()
            }
        }

        addSubGroupBtn.setOnClickListener {
            if (group_spinner.count != 0) { // if a group is selected
                val groupSelected: String = group_spinner.selectedItem as String // get the group selected
                val builder = AlertDialog.Builder(this) //build the dialog
                builder.setTitle("Add a new Sub Group?")
                builder.setMessage("Enter The Sub Group Name: ")

                //declare the input
                val input = EditText(this)
                input.hint = "Name"
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)


                builder.setPositiveButton("Add") { _, _ ->
                    val NewGroupName = input.text.toString() // get the new subgroup name
                    if (inputCheck(NewGroupName)) {
                        //create user object
                        val subGroup = SubGroup(NewGroupName, groupSelected) // create a new subgroup of the group
                        //add data to database
                        mGroupViewModel.addSubGroup(subGroup)
                        Toast.makeText(
                            this,
                            "Successfully added $NewGroupName",
                            Toast.LENGTH_LONG
                        ).show()
                        mGroupViewModel.getSubGroupsOfGroup(groupSelected) // update the subgroup spinner
                    } else {
                        Toast.makeText(
                            this,
                            "Edit Failed, Please fill out name fields",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                builder.setCancelable(false)
                builder.create().show()

            }
        }

        delSubGroupBtn.setOnClickListener {
            if (group_spinner.count != 0 && sub_group_spinner.count != 0) { // if the group and subgroup are selected
                val subGroupSelected: String = sub_group_spinner.selectedItem as String // get the subgroup
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete $subGroupSelected?")
                builder.setMessage("Are you sure you want to delete $subGroupSelected?")


                builder.setPositiveButton("Yes") { _, _ ->
                    mGroupViewModel.deleteSubGroup(subGroupSelected) // delete the subgroup
                    Toast.makeText(
                        this,
                        "Successfully removed $subGroupSelected",
                        Toast.LENGTH_LONG
                    ).show()
                    val groupSelected: String = group_spinner.selectedItem as String//update the subgroup spinner
                    mGroupViewModel.getSubGroupsOfGroup(groupSelected)
                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                builder.setCancelable(false)
                builder.create().show()
            }
        }

        editSubGroupBtn.setOnClickListener {
            if (sub_group_spinner.count != 0) { //if a subgroup is selected
                val subGroupSelected: String = sub_group_spinner.selectedItem as String

                //build the dialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Edit ${subGroupSelected}?")
                builder.setMessage("Change the Group Name of ${subGroupSelected}:")

                //declare the input
                val input = EditText(this)
                input.hint = "Name"
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton("Yes") { _, _ ->
                    val NewGroupName = input.text.toString()  // get the new subgroup name
                    if (inputCheck(NewGroupName)) {
                        mGroupViewModel.updateSubGroup(subGroupSelected,NewGroupName) // update the subgroup with the new name
                        Toast.makeText(
                            this,
                            "Successfully changed to $NewGroupName",
                            Toast.LENGTH_LONG
                        ).show()
                        val groupSelected: String = group_spinner.selectedItem as String
                        mGroupViewModel.getSubGroupsOfGroup(groupSelected)
                    } else { //the subgroup was empty if here
                        Toast.makeText(
                            this,
                            "Add Failed, Please fill out name fields",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                builder.setCancelable(false)
                builder.create().show()
            }
        }


        editConfBtn.setOnClickListener {
            //if the group and the subgroup have items in the spinner and items are selected
            if(group_spinner.selectedItem != null && group_spinner.count != 0 &&
                sub_group_spinner.selectedItem != null && sub_group_spinner.count != 0)
            {
                //get the group name and the subgroup name
                val groupName = group_spinner.selectedItem.toString()
                val subGroupName = sub_group_spinner.selectedItem.toString()
                //set the intent by passing the group name and the subgroup name
                val intent = Intent(this, EditFlashCardsAct::class.java)
                intent.putExtra("group_name", groupName)
                intent.putExtra("sub_group_name", subGroupName)
                startActivity(intent)
            }
            else
            { // if nothing is selected then there should be an error
                Toast.makeText(
                    this,
                    "Please Select a Subject and Cardset",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


        val Backbtn = findViewById<Button>(R.id.Backbutton)
        //this button returns back to the home page
        Backbtn.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun inputCheck(groupName: String): Boolean { //this checks the input
        return !(TextUtils.isEmpty(groupName))
    }
}