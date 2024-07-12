package com.example.dictionary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.databinding.MeaningRecyclerviewBinding
import com.example.dictionary.model.Meaning

class MeaningAdapter() : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {
    private var meanings: List<Meaning> = emptyList()

    inner class MeaningViewHolder(private val binding: MeaningRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meaning: Meaning) {
            binding.partOfSpeechTextview.text = meaning.partOfSpeech
            binding.definitionTextview.text = meaning.definitions.joinToString("\n\n") {
                val currentIndex = meaning.definitions.indexOf(it)
                (currentIndex + 1).toString() + ". " + it.definition
            }

            if(meaning.synonyms.isEmpty()){
                binding.synonymsTitleTextview.visibility=View.GONE
                binding.synonymsTextview.visibility=View.GONE
            }
            else{
                binding.synonymsTitleTextview.visibility=View.VISIBLE
                binding.synonymsTextview.visibility=View.VISIBLE
                binding.synonymsTextview.text = meaning.synonyms.joinToString(", ")
            }

            if(meaning.antonyms.isEmpty()){
                binding.antonymsTitleTextview.visibility=View.GONE
                binding.antonymsTextview.visibility=View.GONE
            }
            else{
                binding.antonymsTitleTextview.visibility=View.VISIBLE
                binding.antonymsTextview.visibility=View.VISIBLE
                binding.synonymsTextview.text = meaning.synonyms.joinToString(", ")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding =
            MeaningRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meanings[position])
    }

    override fun getItemCount(): Int {
        return meanings.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Meaning>) {
        meanings = newList
        notifyDataSetChanged()
    }
}