package com.example.dictionary

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import java.util.Locale


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var binding: ActivityMainBinding
    lateinit var meaningAdapter: MeaningAdapter
    private lateinit var viewModel: MeaningViewmodel
    private lateinit var textToSpeech: TextToSpeech

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

        textToSpeech = TextToSpeech(this, this)

        binding.searchBtn.setOnClickListener {
            hideKeyboard()
            val word = binding.searchText.text.toString()
            if (word.isNotEmpty()) {
                viewModel.loadData(word)
            } else {
                Toast.makeText(this, "Please enter a word to search", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.result.observe(this) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val dictionaryResponse = apiResponse.data
                    if (dictionaryResponse.isNotEmpty()) {
                        val firstResult = dictionaryResponse[0]
                        binding.wordTextview.text = firstResult.word
                        binding.speak.visibility = View.VISIBLE
                        binding.speak.setOnClickListener { speak(firstResult.word) }
                        binding.phoneticTextview.text = firstResult.phonetic
                        meaningAdapter.submitList(firstResult.meanings)
                    } else {
                        Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
                    }
                }

                is ApiResponse.Error -> {
                    Toast.makeText(this, "Error: ${apiResponse.errorCode}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.loading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.searchBtn.visibility = if (loading) View.GONE else View.VISIBLE
        }

    }


    private fun speak(word: String) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)

    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show()
        }
    }


}
