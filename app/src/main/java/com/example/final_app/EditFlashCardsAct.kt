package com.example.final_app

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.final_app.dataGroup.FlashCard

import com.example.final_app.dataGroup.GroupViewModel
import kotlinx.android.synthetic.main.activity_edit_flash_cards.*

class EditFlashCardsAct : AppCompatActivity() {

    private lateinit var mGroupViewModel: GroupViewModel // this is the object to access the group viewmodel to access the database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_flash_cards)

        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java) // get the view model

        val intent = intent.extras // this gets the intent that was passed to this activity
        //the following two variables stores the groups and subgroups names from the intent
        var groupName:String = ""
        var subGroupName:String = ""
        if (intent != null){ // if there is something in the intent
            //read the group name and subgroup name
            groupName = intent.getString("group_name").toString()
            subGroupName = intent.getString("sub_group_name").toString()
            FlashCardsSubGroupET.setText(subGroupName) //update teh text of the textview to match the subgroup
        }

        val Add_FC_Btn = findViewById<Button>(R.id.Add_Flashcard)
        val Edit_Flashcard_Btn = findViewById<Button>(R.id.Edit_Flashcard)
        val Del_FC_Btn = findViewById<Button>(R.id.Del_Flashcard)

        var adapter = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item) // this is the adapter for the spinners to hold all the flashcards

        //this observes live data so that if it changes the adapter is updated with the data
        mGroupViewModel.returnedFlashCards.observe(this, { group ->
            adapter.clear()
            group?.forEach {
                adapter.add(it.front)
            }
        })


        Add_FC_Btn.setOnClickListener {
            //create a dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add a new flashcard?")
            builder.setMessage("Enter the text for the front and the back of the flashcard: ")

            // set the custom layout
            val customLayout = getLayoutInflater().inflate(R.layout.custom_add_flashcard_layout, null);
            builder.setView(customLayout);

            //set the front and back text
            var frontText = customLayout.findViewById<EditText>(R.id.frontText)
            var backText = customLayout.findViewById<EditText>(R.id.backText)
            builder.setPositiveButton("Add") { _, _ ->
                val front = frontText.text.toString()
                val back = backText.text.toString()
                if (inputCheck(front) && inputCheck(back)) {
                    //create Flashcard object
                    val newFlashCard = FlashCard(
                        front,
                        back,
                        subGroupName
                    )


                    //add data to database
                    mGroupViewModel.addFlashcard(newFlashCard)
                    Toast.makeText(this, "Successfully added FlashCard", Toast.LENGTH_LONG)
                        .show()
                } else { // if one of the fields are empty then show an error
                    Toast.makeText(
                        this,
                        "Add Failed, Please fill out both fields",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

            builder.setCancelable(false)

            builder.create().show()
        }

        Edit_Flashcard_Btn.setOnClickListener {
            //get the custom dialog layout
            val customLayout =
                getLayoutInflater().inflate(R.layout.custom_edit_flashcard_layout, null);
            var spinner = customLayout.findViewById<Spinner>(R.id.selectFCSpinner)
            var frontText = customLayout.findViewById<EditText>(R.id.editFrontText)
            var backText = customLayout.findViewById<EditText>(R.id.editBackText)
            mGroupViewModel.getFlashcards(subGroupName) // get the flashcards of the selected subgroup

            //build the dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Edit a flashcard?")
            builder.setMessage("Select a flashcard to edit: ")

            // set the custom layout

            builder.setView(customLayout)
            spinner.adapter = adapter //set the adapter with the flashcards



            //here if the button is pressed and an item is selected, update the flashcard
            builder.setPositiveButton("Edit") { _, _ ->
                val front = frontText.text.toString()
                val back = backText.text.toString()
                if (spinner != null && spinner.selectedItem != null && inputCheck(front) && inputCheck(back)) {
                    val item = spinner.selectedItem.toString()

                    mGroupViewModel.editFlashcard(item,front,back)
                    Toast.makeText(this, "Successfully edited item", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "Edit Failed, Please select a flashcard to edit",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

            builder.setCancelable(false)

            builder.create().show()
        }

        Del_FC_Btn.setOnClickListener {
            //get the custom layout
            val customLayout =
                getLayoutInflater().inflate(R.layout.custom_delete_flashcard_layout, null);
            var spinner = customLayout.findViewById<Spinner>(R.id.deleteFCSpinner)

            mGroupViewModel.getFlashcards(subGroupName) // get the flashcards of the subgroup

            //build the dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete a flashcard?")
            builder.setMessage("Select a flashcard to delete: ")

            // set the custom layout

            builder.setView(customLayout)
            spinner.adapter = adapter // set the spinner data to the flashcards

            builder.setPositiveButton("Delete") { _, _ ->

                if (spinner != null && spinner.selectedItem != null) {
                    val item = spinner.selectedItem.toString()

                    //if an item is selected and the button is pressed, then the item should be deleted from the database
                    mGroupViewModel.deleteFlashcard(item)
                    Toast.makeText(this, "Successfully deleted item", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "Delete Failed, Please select a flashcard to delete",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

            builder.setCancelable(false)

            builder.create().show()

        }
        val Backbtn = findViewById<Button>(R.id.Backbutton)

        //this takes the user back to the home activity
        Backbtn.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    private fun inputCheck(groupName: String): Boolean { // this checks if the item sent to it is empty, if so it returns false, else if it is not empty return true
        return !(TextUtils.isEmpty(groupName))
    }
}