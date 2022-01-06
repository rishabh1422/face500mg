package com.example.face500mg.Adapter

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.face500mg.Adapter.SearchResultAdapter.MyViewHolder
import com.example.face500mg.R
import com.example.face500mg.data.data
import com.example.face500mg.databinding.MatchListBinding


class SearchResultAdapter ( private var li: ArrayList<data>):
    RecyclerView.Adapter<MyViewHolder>() {
//    private lateinit var mListener: onItemClickListener
////    interface onItemClickListener
////    {
////        fun onItemClick(position: Int)
////    }
//    fun SetOnIemClickListener(listener: onItemClickListener)
//    {
//        mListener=listener
//    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding= MatchListBinding.bind(view)
        // var name: TextView = view.findViewById(R.id.tvTitle)
        //var collage: TextView = view.findViewById(R.id.tvFees)
//        init {
//            view.setOnClickListener{
//                listener.onItemClick(adapterPosition)
//            }


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.match_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pending = li[position]
        holder.binding.mp.text=pending.name
    }

    override fun getItemCount(): Int {
        return li.size
    }



}
