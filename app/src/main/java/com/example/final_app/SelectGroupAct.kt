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

class SelectGroupAct : AppCompatActivity() {
    private lateinit var mGroupViewModel: GroupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectgroup)

        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        val editConfBtn = findViewById<Button>(R.id.EditConfirmBtn)
        val editGroupBtn = findViewById<Button>(R.id.GoToEdit_AddBtn)

        val addGroupBtn: View = findViewById(R.id.add_group_button)
        val delGroupBtn: View = findViewById(R.id.delete_group_button)

        val group_spinner = findViewById<Spinner>(R.id.GroupSpinner)

        var group_adapter = ArrayAdapter<Group>(this,android.R.layout.simple_spinner_item)

        mGroupViewModel.readAllData.observe(this, Observer {  group ->
            group_adapter.clear()
            group?.forEach {
                group_adapter.add(it)
            }
        })

        group_spinner.adapter = group_adapter




        addGroupBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add a new group?")
            builder.setMessage("Enter The Group Name: ")

            val input = EditText(this)
            input.setHint("Name")

            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)


            builder.setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                val NewGroupName = input.text.toString()
                insertDataToDatabase(NewGroupName)

            })

            builder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->  dialog.cancel()})

            builder.setCancelable(false)

            builder.create().show()
        }

        delGroupBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val itemSelected:Group = group_spinner.selectedItem as Group
            builder.setPositiveButton("Yes") { _, _ ->

                mGroupViewModel.deleteGroup(itemSelected)
                Toast.makeText(this,"Successfully removed ${itemSelected.groupName}",Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->  dialog.cancel()})
            builder.setTitle("Delete ${itemSelected.groupName}?")
            builder.setMessage("Are you sure you want to delete ${itemSelected.groupName}?")
            builder.create().show()
        }



        editConfBtn.setOnClickListener {
            startActivity(Intent(this, EditFlashCardsAct::class.java))
        }



    }

    private fun insertDataToDatabase(groupName: String) {


        if (inputCheck(groupName)){
            //create user object
            val group = Group(0,groupName)
            //add data to databasem
            mGroupViewModel.addGroup(group)
            Toast.makeText(this,"Successfully added ${groupName}", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"Please fill out name fields",Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(groupName:String):Boolean{
        return !(TextUtils.isEmpty(groupName))
    }
}