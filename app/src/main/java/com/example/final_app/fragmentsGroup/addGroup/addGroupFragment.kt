package com.example.final_app.fragmentsGroup.addGroup

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_app.R
import com.example.final_app.dataGroup.Group
import com.example.final_app.dataGroup.GroupViewModel
import kotlinx.android.synthetic.main.fragment_add_group.*
import kotlinx.android.synthetic.main.fragment_add_group.view.*

private lateinit var mGroupViewModel:GroupViewModel

class addGroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_group, container, false)

        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        view.addGroupBtn.setOnClickListener {
            insertDataToDatabase()
        }



        return view
    }

    private fun insertDataToDatabase() {
        val groupName = addGroupName_et.text.toString()

        if (inputCheck(groupName)){
            //create user object
            val group = Group(0,groupName)
            //add data to database
            mGroupViewModel.addGroup(group)
            Toast.makeText(requireContext(), "Successfully added!",Toast.LENGTH_LONG).show()
            // navigate back
            findNavController().navigate(R.id.action_addGroupFragment_to_listGroupFragment)
        }
        else{
            Toast.makeText(requireContext(),"Please fill out all fields",Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(groupName:String):Boolean{
        return !(TextUtils.isEmpty(groupName))
    }


}