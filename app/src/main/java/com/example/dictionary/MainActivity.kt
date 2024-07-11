package com.example.dictionary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.adapter.MeaningAdapter
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.viewmodel.MeaningViewmodel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var meaningAdapter: MeaningAdapter
    private lateinit var viewModel: MeaningViewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[MeaningViewmodel::class.java]

        binding.meaningRecyclerview.layoutManager = LinearLayoutManager(this)
        meaningAdapter = MeaningAdapter()
        binding.meaningRecyclerview.adapter = meaningAdapter

        binding.searchBtn.setOnClickListener {
            val word = binding.searchText.text.toString()
            if (word.isNotEmpty()) {
                viewModel.loadData(word)
            } else {
                Toast.makeText(this, "Please enter a word to search", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.result.observe(this) { result ->

            binding.wordTextview.text = result.word
            binding.phoneticTextview.text = result.phonetic
        }
    }


}
