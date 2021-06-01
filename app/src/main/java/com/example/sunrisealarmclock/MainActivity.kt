package com.example.sunrisealarmclock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.example.sunrisealarmclock.notification.NotificationSender
import com.example.sunrisealarmclock.settings.SettingsViewModel


class MainActivity : AppCompatActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navigateToStartFragment()
        //createNotificationSender()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->{
                findNavController(R.id.nav_host_fragment).navigate(R.id.SettingsFragment)
                true
            }
            else -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.HomeFragment)
                true
            }
        }
    }

    private fun navigateToStartFragment(){
        if (settingsViewModel.timezoneChosen){
            findNavController(R.id.nav_host_fragment).navigate(R.id.HomeFragment)
        }
        else{
            findNavController(R.id.nav_host_fragment).navigate(R.id.TimezoneFragment)
        }
    }

    private fun createNotificationSender(){
        val notificationSender = NotificationSender(this)
        notificationSender.sendAlarmNotification()
    }
}