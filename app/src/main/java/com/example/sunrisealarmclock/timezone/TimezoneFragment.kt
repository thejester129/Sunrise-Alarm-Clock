package com.example.sunrisealarmclock.timezone

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.sunrisealarmclock.R
import com.example.sunrisealarmclock.settings.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_timezone.*

class TimezoneFragment : Fragment() {
    private val timezoneViewModel: TimezoneViewModel by activityViewModels()
    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_timezone, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_settings).isVisible = false
        val activity = requireActivity() as AppCompatActivity
        val supportActionBar = activity.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timezoneViewModel.dataLoading.observe(viewLifecycleOwner,  { isLoading ->
            if(isLoading){
                loading_bar.visibility = View.VISIBLE
            }
            else{
                loading_bar.visibility = View.GONE
                createTimezoneObserver()
            }
        })

    }

    private fun createTimezoneObserver(){
        timezoneViewModel.liveTimezones.observe(viewLifecycleOwner,  { timezones ->
            if(timezones != null){
                setupSpinner(timezones)
                setupContinueButton()
            }
        })
    }

    private fun setupSpinner(timezones: Array<TimezoneLocation>){
        timezone_spinner_label.visibility = View.VISIBLE
        timezone_spinner.visibility = View.VISIBLE
        val items = timezones.map {item -> "${item.city}  ${item.offset} GMT"}
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timezone_spinner.adapter = adapter
    }

    private fun setupContinueButton(){
        timezone_continue_button.visibility = View.VISIBLE
        timezone_continue_button.setOnClickListener {
            val spinnerPos = timezone_spinner.selectedItemPosition
            val timezone = timezoneViewModel.timezones[spinnerPos]
            timezoneViewModel.setTimezone(timezone)
            settingsViewModel.timezoneChosen = true
            view?.findNavController()?.navigate(R.id.HomeFragment)
        }
    }

}