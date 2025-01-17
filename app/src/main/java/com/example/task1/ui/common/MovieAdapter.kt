package com.example.task1.ui.common

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.model.Movie
import com.example.task1.R
import com.example.task1.databinding.CustomCardViewBinding

class MovieAdapter(private val movieClickedListener : MovieClicked, private val context : Context, private val movies : List<Movie>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CustomCardViewBinding.inflate(inflater,parent,false)

        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MyViewHolder(private val binding : CustomCardViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie : Movie){


            if(movie.name.length >= 15){
                binding.movieTitle.textSize=20f
                val params = binding.movieRate.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin=200
                val params1 = binding.rateImg.layoutParams as ViewGroup.MarginLayoutParams
                params1.topMargin=200
            }
            binding.movieTitle.text = movie.name


            if (!binding.categoryLayout.isEmpty()) {
                binding.categoryLayout.removeAllViews()
            }



            for (i in movie.genres.indices) {
                val textView = TextView(context)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )

                textView.layoutParams = layoutParams
                textView.text = movie.genres[i]

                val textSize = when {
                    movie.genres[i].length <= 8 -> 15f
                    else -> 12f
                }

                val width = 300
                val height = 120


                layoutParams.rightMargin = 7
                textView.width = width
                textView.height = height

                textView.textSize = textSize
                val textColor = ContextCompat.getColor(context, R.color.textColor)
                textView.setTextColor(textColor)
                textView.gravity = Gravity.CENTER
                textView.background = ContextCompat.getDrawable(context, R.drawable.custom_btn)
                binding.categoryLayout.addView(textView)

                binding.movieCardView.setOnClickListener{
                    movieClickedListener.onMovieClicked(movie)
                }
            }

            val hours = movie.runtime / 60
            val mins = movie.runtime % 60

            binding.duration.text=context.getString(R.string.movieLength,hours.toString(),mins.toString())
            binding.movieRate.text= context.getString(R.string.rating,movie.rating.average.toString())


            val imageUrl =movie.image.medium
            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.baseline_error_24)
                .into(binding.movieImage)
        }
    }

}

interface MovieClicked {
    fun onMovieClicked(movieData : Movie)
}