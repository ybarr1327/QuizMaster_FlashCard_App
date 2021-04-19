package com.example.final_app.fragmentsGroup.listGroup

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.final_app.R
import com.example.final_app.dataGroup.Group
import kotlinx.android.synthetic.main.custom_row_group.view.*


class ListAdapterGroup:RecyclerView.Adapter<ListAdapterGroup.MyViewHolder>() {

    private var groupList = emptyList<Group>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_group, parent, false))
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = groupList[position]
        holder.itemView.IdNum_TV.text = currentItem.id.toString()
        holder.itemView.GroupName_TV.text = currentItem.groupName


    }

    fun setData(group:List<Group>){
        this.groupList =group
        notifyDataSetChanged()
    }

}