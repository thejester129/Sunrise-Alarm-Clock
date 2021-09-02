package com.example.sunrisealarmclock.days

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrisealarmclock.R
import com.example.sunrisealarmclock.settings.SettingsViewModel

class DaysRecyclerViewAdapter(private val dataSet: Array<DayItem>, private val viewModel : SettingsViewModel) :
    RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder>() {
    private val activeColour = Color.parseColor("#15d4a4")
    private val inactiveColour = Color.parseColor("#FFFFFF")

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.day_recycler_item, viewGroup, false)

        return ViewHolder(view).listen { pos ->
            val day = dataSet[pos]
            day.active = !day.active
            viewModel.toggleDayOfWeekActive(day.dayOfWeek)
            notifyItemChanged(pos)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val day = dataSet[position]
        viewHolder.dayLetter.text = day.dayLetter.toString()
        if(day.active){
            viewHolder.dayLetter.setTextColor(activeColour)
            viewHolder.activeCircle.visibility = View.VISIBLE
        }
        else{
            viewHolder.dayLetter.setTextColor(inactiveColour)
            viewHolder.activeCircle.visibility = View.GONE
        }
    }

    override fun getItemCount() = dataSet.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayLetter: TextView = view.findViewById(R.id.day_recycler_item_letter)
        val activeCircle: ImageView = view.findViewById(R.id.day_recycler_item_active_circle)
    }

    private fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition)
        }
        return this
    }

}
