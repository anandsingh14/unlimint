package com.anandsingh.joketestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anandsingh.joketestapp.databinding.AdapterJokeBinding

class MovieAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var jokeList = mutableListOf<Joke>()

    fun setMovies(movies: List<Joke>) {
        this.jokeList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterJokeBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val movie = jokeList[position]
        if (ValidationUtil.validateMovie(movie)) {
            holder.binding.name.text = movie.joke
        }
    }

    override fun getItemCount(): Int {
        return jokeList.size
    }
}

class MainViewHolder(val binding: AdapterJokeBinding) : RecyclerView.ViewHolder(binding.root) {

}