package com.example.final_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class EditAddRemoveGroups : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_remove_groups)

        setupActionBarWithNavController(findNavController(R.id.fragmentGroup))


    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentGroup)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
    }
}