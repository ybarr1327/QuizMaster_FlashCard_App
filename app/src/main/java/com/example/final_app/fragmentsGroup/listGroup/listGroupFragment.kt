package com.example.final_app.fragmentsGroup.listGroup

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_app.R
import com.example.final_app.dataGroup.GroupViewModel
import kotlinx.android.synthetic.main.fragment_list_group.view.*


class listGroupFragment : Fragment() {

    private lateinit var mGroupViewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_group, container, false)

        //recyclerview
        val adapter = ListAdapterGroup()
        val recyclerView = view.recyclerviewGroups
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //userviewmodel
        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        mGroupViewModel.readAllData.observe(viewLifecycleOwner, Observer {  group ->
            adapter.setData(group)
        })


        view.floatingActionButtonGroups.setOnClickListener {
            findNavController().navigate(R.id.action_listGroupFragment_to_addGroupFragment)
        }

        setHasOptionsMenu(true)


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu_group, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_group){
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mGroupViewModel.deleteAllGroups()
            Toast.makeText(requireContext(),"Sucessfully removed everything",Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()

    }
}