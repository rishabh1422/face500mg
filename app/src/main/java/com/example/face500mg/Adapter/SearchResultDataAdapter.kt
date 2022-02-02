package com.example.face500mg.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.face500mg.data.ImageInfor
import com.example.face500mg.databinding.MatchListBinding

class SearchResultDataAdapter(
    private var plans: List<ImageInfor>,
    var listiner: OnClick
) :
    RecyclerView.Adapter<SearchResultDataAdapter.ViewHolder>() {



    class ViewHolder(var binding: MatchListBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewholder =
           MatchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewholder)
    }


    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {

        val data = plans[position]
        Glide.with(holder.itemView.context).load(data.tmpurl).into(holder.binding.iv)
        holder.binding.mp.text=data.custId.toString()
        holder.itemView.setOnClickListener {
            listiner.onClick(data.custId)
        }


    }

    override fun getItemCount(): Int {
        return plans.size
    }
    interface OnClick {
        fun onClick(data: Int)
    }
    }