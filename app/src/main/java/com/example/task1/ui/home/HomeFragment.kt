package com.example.task1.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task1.utils.Constants
import com.example.task1.ui.common.MovieAdapter
import com.example.task1.model.Movie
import com.example.task1.ui.movieDetails.MovieActivity
import com.example.task1.ui.common.MovieClicked
import com.example.task1.databinding.HomeFragmentAllMoviesBinding


class HomeFragment : Fragment(), MovieClicked {

    private lateinit var myCustomAdapter: MovieAdapter
    private var moviesList = mutableListOf<Movie>()

    private var _binding : HomeFragmentAllMoviesBinding?=null
    private val binding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentAllMoviesBinding.inflate(inflater,container,false)


        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
    }


    private fun initializeRecyclerView(){
        binding.homeFragmentRecView.layoutManager=LinearLayoutManager(context)

        moviesList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arguments?.getParcelableArrayList(Constants.MOVIES_LIST, Movie::class.java)!!
        } else {
            arguments?.getParcelableArrayList(Constants.MOVIES_LIST)!!
        }

        if(activity != null){
            myCustomAdapter = MovieAdapter(this@HomeFragment, requireActivity(),moviesList)
            binding.homeFragmentRecView.adapter=myCustomAdapter
        }

    }


    override fun onMovieClicked(movieData : Movie) {
        val intent = Intent(context, MovieActivity::class.java)
        intent.putExtra(Constants.MOVIE_KEY,movieData)
        startActivity(intent)
    }
}