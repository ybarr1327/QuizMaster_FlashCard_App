package com.example.final_app.fragmentsGroup.listGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return view
    }


}