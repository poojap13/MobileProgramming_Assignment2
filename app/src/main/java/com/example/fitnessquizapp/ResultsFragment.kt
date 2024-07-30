package com.example.fitnessquizapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_results, container, false)
        val resultsText: TextView = root.findViewById(R.id.results_text)

        val sharedPreferences = requireContext().getSharedPreferences("quiz_history", Context.MODE_PRIVATE)
        val lastResult = sharedPreferences.getString("last_result", "No results yet.")
        resultsText.text = lastResult

        return root
    }
}
