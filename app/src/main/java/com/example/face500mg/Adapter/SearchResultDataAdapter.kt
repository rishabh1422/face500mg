package com.example.face500mg.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.face500mg.data.ImageInfor
import com.example.face500mg.databinding.MatchListBinding

class SearchResultDataAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var movies = mutableListOf<ImageInfor>()

    fun setMovieList(movies: List<ImageInfor>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = MatchListBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies[position]

        Glide.with(holder.itemView.context).load(movie.tmpurl).into(holder.binding.iv)

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MainViewHolder(val binding: MatchListBinding) : RecyclerView.ViewHolder(binding.root) {

}