package com.example.task1.ui.bookmark

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task1.utils.Constants
import com.example.task1.ui.common.MovieAdapter
import com.example.task1.model.Movie
import com.example.task1.ui.movieDetails.MovieActivity
import com.example.task1.ui.common.MovieClicked
import com.example.task1.utils.SharedPrefManager
import com.example.task1.broadcasts.BroadcastNotifyAnUpdate
import com.example.task1.databinding.FragmentBookmarkedMoviesBinding
import com.example.task1.ui.home.viewModels.RoomViewModel

class BookmarkedMoviesFragment : Fragment(), MovieClicked, BroadcastNotifyAnUpdate.BroadcastReceiverListener {

    private val bookmarkedMoviesList = mutableListOf<Movie>()
    private lateinit var databaseViewModel : RoomViewModel
    private lateinit var myCustomAdapter: MovieAdapter
    private var moviesList : MutableList<Movie>? = mutableListOf<Movie>()
    private lateinit var broadcastReceiver : BroadcastNotifyAnUpdate
    private var _binding : FragmentBookmarkedMoviesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkedMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(activity != null)
            requireActivity().unregisterReceiver(broadcastReceiver)
        _binding=null
    }

    private fun initialize(){
        setMoviesListValue()
        initializeBroadcast()
        initializeRecyclerView()
      //  initializeSharedPreferences()
        initializeRoomViewModel()
        updateBookmarkList()
    }

    private fun initializeRoomViewModel(){
        databaseViewModel = ViewModelProvider(this)[RoomViewModel::class.java]
    }


    private fun setMoviesListValue(){
        moviesList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arguments?.getParcelableArrayList(Constants.MOVIES_LIST, Movie::class.java)
        } else {
            arguments?.getParcelableArrayList(Constants.MOVIES_LIST)
        }
    }

    private fun initializeBroadcast(){
        broadcastReceiver = BroadcastNotifyAnUpdate(this)
        val filter = IntentFilter(Constants.DELETE_MOVIE_ACTION)
        val receiverFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Context.RECEIVER_NOT_EXPORTED
        } else {
            0
        }
        if(activity != null){
            requireActivity().registerReceiver(broadcastReceiver, filter, receiverFlags)
        }
    }

    private fun initializeRecyclerView(){
        binding.recyclerViewBookmarkedFragment.layoutManager= LinearLayoutManager(context)
      //  bookmarkedMoviesList= mutableListOf()
    }

    private fun updateBookmarkList(){
        databaseViewModel.allData.observe(viewLifecycleOwner, Observer {listOfMovies ->
            bookmarkedMoviesList.clear()
            bookmarkedMoviesList.addAll(listOfMovies)
            if(bookmarkedMoviesList.isEmpty()){
                binding.recyclerViewBookmarkedFragment.visibility=View.GONE
                binding.emptyTextBookmarkedFragment.visibility=View.VISIBLE
            }

            else {
                if(context != null) {
                    binding.recyclerViewBookmarkedFragment.visibility = View.VISIBLE
                    binding.emptyTextBookmarkedFragment.visibility = View.GONE
                    myCustomAdapter = MovieAdapter(this@BookmarkedMoviesFragment, requireContext(), bookmarkedMoviesList)
                    binding.recyclerViewBookmarkedFragment.adapter = myCustomAdapter
                }
            }
        })
    }

    override fun onMovieClicked(movieData: Movie) {
        val intent = Intent(context, MovieActivity::class.java)
        intent.putExtra(Constants.MOVIE_KEY,movieData)
        startActivity(intent)
    }


    override fun onBroadcastReceived(id : Int) {
        databaseViewModel.deleteMovie(id)
        updateBookmarkList()
    }

}

