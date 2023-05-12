package com.anandsingh.joketestapp

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anandsingh.joketestapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private val adapter = MovieAdapter()
    lateinit var binding: ActivityMainBinding
    var mutableListString: MutableList<Joke> = mutableListOf<Joke>()
    var mutableListString1: MutableList<Joke> = mutableListOf<Joke>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)


        viewModel.JokeList.observe(this) {
            mutableListString.clear()
            mutableListString.addAll(mutableListString1)
            mutableListString.add(it)

            mutableListString1.clear()

            mutableListString1.addAll(mutableListString)

            if (mutableListString1.size==11){
                mutableListString1.removeAt(0)
            }

            adapter.setMovies(mutableListString1)
            binding.progressDialog.visibility = View.GONE
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        startRepeatingJob()

    }

    @OptIn(InternalCoroutinesApi::class)
    private fun startRepeatingJob(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.isActive) {
                viewModel.getAllMovies()
                delay(5000)
            }
        }
    }
}