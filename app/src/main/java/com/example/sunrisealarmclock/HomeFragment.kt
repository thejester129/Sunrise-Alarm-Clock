package com.example.sunrisealarmclock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.view.get
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNumberPicker()
    }

    private fun setupNumberPicker(){
        number_picker_minutes.maxValue = 6
        number_picker_minutes.displayedValues = arrayOf("5", "10", "15", "20", "30", "45", "60")

        number_picker_minutes.setOnValueChangedListener { _, _, index ->
            val mins : String = number_picker_minutes.displayedValues[index]
            viewModel.setMinsBeforeSunrise(mins.toInt())
        }
    }
}