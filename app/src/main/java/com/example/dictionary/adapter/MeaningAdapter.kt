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

    inner class MeaningViewHolder(private val binding: MeaningRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meaning: Meaning) {
            binding.partOfSpeechTextview.text = meaning.partOfSpeech
            binding.definitionTextview.text = meaning.definitions.joinToString(", ") { it.definition }
            binding.synonymsTextview.text = meaning.synonyms.joinToString(", ")
            binding.antonymsTextview.text = meaning.antonyms.joinToString(", ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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