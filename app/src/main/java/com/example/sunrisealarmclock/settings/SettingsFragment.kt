package com.example.sunrisealarmclock.settings

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.sunrisealarmclock.R
import com.example.sunrisealarmclock.timezone.TimezoneViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    private val timezoneViewModel: TimezoneViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_settings).isVisible = false
        val actionBar = requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timezoneViewModel.dataLoading.observe(viewLifecycleOwner, Observer { loaded ->
            if(loaded){
                setupSpinner()
            }
        })
        setupSpinner()
    }

    private fun setupSpinner(){
        val items = timezoneViewModel.timezones.map {item -> "${item.city}  ${item.offset} GMT"}
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        settings_timezone_spinner.adapter = adapter
        settings_timezone_spinner.onItemSelectedListener = SpinnerListener(timezoneViewModel)
        val currentIndex = timezoneViewModel.timezones.indexOf(timezoneViewModel.timezoneLocation)
        settings_timezone_spinner.setSelection(currentIndex)
    }

    private class SpinnerListener(private val timezoneViewModel: TimezoneViewModel) : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val timezone = timezoneViewModel.timezones[position]
            timezoneViewModel.setTimezone(timezone)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}