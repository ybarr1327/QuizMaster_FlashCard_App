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

        addGroupBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add a new group?")
            builder.setMessage("Enter The Group Name: ")

            val input = EditText(this)
            input.hint = "Name"
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)


            builder.setPositiveButton("Add") { _, _ ->
                val NewGroupName = input.text.toString()
                if (inputCheck(NewGroupName)) {
                    //create user object
                    val group = Group(NewGroupName)
                    //add data to database
                    mGroupViewModel.addGroup(group)
                    Toast.makeText(this, "Successfully added $NewGroupName", Toast.LENGTH_LONG)
                        .show()

                    if (group_spinner.count != 0) {
                        val newItemSelected: String = group_spinner.selectedItem as String
                        mGroupViewModel.getSubGroupsOfGroup(newItemSelected)
                    }
                } else {
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
            if (group_spinner.count != 0) {
                val itemSelected: String = group_spinner.selectedItem as String
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete $itemSelected?")
                builder.setMessage("Are you sure you want to delete $itemSelected?")


                builder.setPositiveButton("Yes") { _, _ ->

                    mGroupViewModel.deleteGroup(itemSelected)
                    Toast.makeText(
                        this,
                        "Successfully removed $itemSelected",
                        Toast.LENGTH_LONG
                    ).show()
                    if (group_spinner.count != 0) {
                        val newItemSelected: String = group_spinner.selectedItem as String
                        mGroupViewModel.getSubGroupsOfGroup(newItemSelected)
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
            if (group_spinner.count != 0) {
                val itemSelectedName: String = group_spinner.selectedItem as String


                val builder = AlertDialog.Builder(this)
                builder.setTitle("Edit ${itemSelectedName}?")
                builder.setMessage("Change the Group Name of ${itemSelectedName}:")

                val input = EditText(this)
                input.hint = "Name"
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton("Yes") { _, _ ->
                    val NewGroupName = input.text.toString()
                    if (inputCheck(NewGroupName)) {
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
            if (group_spinner.count != 0) {
                val groupSelected: String = group_spinner.selectedItem as String
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Add a new Sub Group?")
                builder.setMessage("Enter The Sub Group Name: ")

                val input = EditText(this)
                input.hint = "Name"
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)


                builder.setPositiveButton("Add") { _, _ ->
                    val NewGroupName = input.text.toString()
                    if (inputCheck(NewGroupName)) {
                        //create user object
                        val subGroup = SubGroup(NewGroupName, groupSelected)
                        //add data to database
                        mGroupViewModel.addSubGroup(subGroup)
                        Toast.makeText(
                            this,
                            "Successfully added $NewGroupName",
                            Toast.LENGTH_LONG
                        ).show()
                        mGroupViewModel.getSubGroupsOfGroup(groupSelected)
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
            if (group_spinner.count != 0 && sub_group_spinner.count != 0) {
                val subGroupSelected: String = sub_group_spinner.selectedItem as String
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete $subGroupSelected?")
                builder.setMessage("Are you sure you want to delete $subGroupSelected?")


                builder.setPositiveButton("Yes") { _, _ ->
                    mGroupViewModel.deleteSubGroup(subGroupSelected)
                    Toast.makeText(
                        this,
                        "Successfully removed $subGroupSelected",
                        Toast.LENGTH_LONG
                    ).show()
                    val groupSelected: String = group_spinner.selectedItem as String
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
            if (sub_group_spinner.count != 0) {
                val subGroupSelected: String = sub_group_spinner.selectedItem as String


                val builder = AlertDialog.Builder(this)
                builder.setTitle("Edit ${subGroupSelected}?")
                builder.setMessage("Change the Group Name of ${subGroupSelected}:")

                val input = EditText(this)
                input.hint = "Name"
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton("Yes") { _, _ ->
                    val NewGroupName = input.text.toString()
                    if (inputCheck(NewGroupName)) {
                        mGroupViewModel.updateSubGroup(subGroupSelected,NewGroupName)
                        Toast.makeText(
                            this,
                            "Successfully changed to $NewGroupName",
                            Toast.LENGTH_LONG
                        ).show()
                        val groupSelected: String = group_spinner.selectedItem as String
                        mGroupViewModel.getSubGroupsOfGroup(groupSelected)
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


        editConfBtn.setOnClickListener {
            if(group_spinner.selectedItem != null && group_spinner.count != 0 &&
                sub_group_spinner.selectedItem != null && sub_group_spinner.count != 0)
            {
                val groupName = group_spinner.selectedItem.toString()
                val subGroupName = sub_group_spinner.selectedItem.toString()
                val intent = Intent(this, EditFlashCardsAct::class.java)
                intent.putExtra("group_name", groupName)
                intent.putExtra("sub_group_name", subGroupName)
                startActivity(intent)
            }


        }
    }

    private fun inputCheck(groupName: String): Boolean {
        return !(TextUtils.isEmpty(groupName))
    }
}