package com.example.dictionary

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
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

        binding.searchBtn.setOnClickListener {
            val word = binding.searchText.text.toString()
            getResult(word)
        }


    }

    private fun getResult(word: String) {
        getProgressBar(true)
        GlobalScope.launch {
            val response = RetrofitInstance.dictionaryApi.getResult(word)
            runOnUiThread {
                getProgressBar(false)
                response.body()?.first()?.let {
                    setUi(it)
                }
            }
        }

    }

    private fun setUi(result: ResultDataClass) {
        binding.wordTextview.text = result.word
        binding.phoneticTextview.text = result.phonetic

    }

    private fun getProgressBar(progress: Boolean) {
        if (progress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.searchBtn.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.searchBtn.visibility = View.VISIBLE
        }

    }
}