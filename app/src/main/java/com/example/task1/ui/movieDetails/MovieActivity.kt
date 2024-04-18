package com.example.task1.ui.movieDetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.task1.utils.Constants
import com.example.task1.model.Movie
import com.example.task1.R
import com.example.task1.utils.SharedPrefManager
import com.example.task1.databinding.ActivityMovieBinding
import com.example.task1.ui.home.viewModels.RoomViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMovieBinding
    private lateinit var databaseViewModel: RoomViewModel
    private var movie : Movie? = null
    private var id : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        wrapViews()
    }

    private fun wrapViews(){
        initialize()
        binding.bookmarkDis.setOnClickListener {
            checkIfMovieSaved(id) {saved ->
                    if(!saved){
                        binding.bookmarkDis.setImageResource(R.drawable.filled_bookmark)
                        databaseViewModel.addMovie(movie!!)
                        Log.d("00000000000000000","000000000000000000")
                    }
                    else {
                        binding.bookmarkDis.setImageResource(R.drawable.bookmark_disabled)
                        databaseViewModel.deleteMovie(id)
                        val deleteIntent = Intent(Constants.DELETE_MOVIE_ACTION)
                        deleteIntent.putExtra(Constants.ID_TO_SAVE, id)
                        sendBroadcast(deleteIntent)
                        Log.d("111111111111111111111","111111111111111111111")
                    }
            }
        }

        binding.backImg.setOnClickListener {
            finish()
        }
    }

    private fun initialize(){
        initializeViewModel()
        setMovieSavedImage()
    }

    private fun setMovieSavedImage(){
        getMovie()
        setSaveImage()
    }

    private fun getMovie(){
        movie = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.MOVIE_KEY, Movie::class.java)
        }
        else {
            intent.getParcelableExtra(Constants.MOVIE_KEY)
        }
        wrapDataToViews(movie)
    }

    private fun setSaveImage(){
        checkIfMovieSaved(id) { isSaved ->
            if (!isSaved) {
                binding.bookmarkDis.setImageResource(R.drawable.bookmark_disabled)
            } else {
                binding.bookmarkDis.setImageResource(R.drawable.filled_bookmark)
            }
        }
    }



    private fun checkIfMovieSaved(idToCheck : Int,callback: (Boolean) -> Unit){
        var flag = false
        databaseViewModel.allData.observe(this,Observer{savedMovies ->
            for(movie in savedMovies){
                Log.d("The movie id is -> ${movie.id}","The movie id is -> ${movie.id}")
                if(movie.id == idToCheck){
                    flag = true
                }
            }
            callback(flag)
        })
    }

    private fun initializeViewModel(){
        databaseViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
    }

    private fun wrapDataToViews(movie : Movie?){
        if (movie != null) {
            id=movie.id
            Glide.with(this)
                .load(movie.image.medium)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.baseline_error_24)
                .into(binding.movieImg)

            binding.movieName.text=movie.name
            binding.rateText.text=getString(R.string.rating,movie.rating.average.toString())

            val hours = (movie.runtime / 60).toString()
            val mins = (movie.runtime % 60).toString()
            binding.movieLength.text=getString(R.string.movieLength,hours,mins)
            binding.movieLanguage.text=movie.language
            binding.summary.text=movie.summary

            if(!binding.categLayout.isEmpty()){
                binding.categLayout.removeAllViews()
            }
            setupGenres(movie)
        }
    }

    private fun setupGenres(movie : Movie){
        for (x in movie.genres.indices){
            val textView = TextView(this)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.text = movie.genres[x]

            var textSize = when {
                movie.genres[x].length <= 8 -> 16f
                else -> 12f
            }

            if(movie.genres[x].length > 13){
                textView.width = 350
                textSize=13f
            }
            else {
                textView.width = 300
            }

            layoutParams.rightMargin = 18

            textView.height = 120
            textView.textSize = textSize
            val textColor = ContextCompat.getColor(this, R.color.textColor)
            textView.setTextColor(textColor)
            textView.gravity = Gravity.CENTER
            textView.background = ContextCompat.getDrawable(this, R.drawable.custom_btn)
            textView.layoutParams = layoutParams
            binding.categLayout.addView(textView)

        }
    }

}