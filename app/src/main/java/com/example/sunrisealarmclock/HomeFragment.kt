package com.example.sunrisealarmclock

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunrisealarmclock.days.DaysRecyclerViewAdapter
import com.example.sunrisealarmclock.settings.SettingsViewModel
import com.example.sunrisealarmclock.time.TimeViewModel
import com.example.sunrisealarmclock.timezone.TimezoneViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val timeViewModel: TimeViewModel by activityViewModels()
    private val timezoneViewModel:TimezoneViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNumberPicker()
        setupDaysRecycler()
        setupCity()
        observeSunriseTime()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val activity = requireActivity() as AppCompatActivity
        val supportActionBar = activity.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun setupNumberPicker(){
        number_picker_minutes.maxValue = 6
        val minuteValues = arrayOf("0", "5", "10", "20", "30", "45", "60")
        number_picker_minutes.displayedValues = minuteValues
        // set previous value from settings
        val prevVal = settingsViewModel.getAlarmMinsBeforeSunrise()
        val indexOfPrevVal = minuteValues.indexOf(prevVal)
        number_picker_minutes.value = indexOfPrevVal

        number_picker_minutes.setOnValueChangedListener { _, _, index ->
            val mins : String = minuteValues[index]
            settingsViewModel.setAlarmMinsBeforeSunrise(mins)
        }
    }

    private fun setupDaysRecycler(){
        days_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        days_recycler_view.adapter = DaysRecyclerViewAdapter(settingsViewModel.getDays(), settingsViewModel)
    }

    private fun setupCity(){
        timezoneViewModel.timezoneLocation?.let { home_city.text = it.city}
    }

    private fun observeSunriseTime(){
        timeViewModel.sunriseTime.observe(viewLifecycleOwner,  { time ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            val timeString = convertDateToTimeString(calendar) + " a.m."
            home_sunrise_time.text = timeString
        })
    }

    private fun convertDateToTimeString(calendar: Calendar): String {
        val df: DateFormat = SimpleDateFormat("HH:mm")
        val time = calendar.time
        return df.format(time)
    }

}