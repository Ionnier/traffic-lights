package com.example.semafoare.database

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.semafoare.MainFragmentDirections
import com.example.semafoare.R
import com.google.android.material.color.MaterialColors

class TrafficLightAdapter(val context: Context) : ListAdapter<TrafficLight, TrafficLightAdapter.TrafficLightViewHolder>(TrafficLightComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficLightViewHolder {
        return TrafficLightViewHolder.create(context, parent)
    }

    override fun onBindViewHolder(holder: TrafficLightViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class TrafficLightViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewId: TextView = itemView.findViewById(R.id.textViewId)
        private val textViewCoords: TextView = itemView.findViewById(R.id.textViewCoords)
        private val textViewAlt: TextView = itemView.findViewById(R.id.textViewAlt)
        private val textViewUpdate: Button = itemView.findViewById(R.id.textViewUpdate)
        private val textViewCreation: TextView = itemView.findViewById(R.id.textViewCreation)

        fun bind(trafficLight: TrafficLight) {
            textViewId.text=String.format(context.resources.getString(R.string.traffic_light_id), trafficLight.trafficLightId)
            if(trafficLight.latitude!=null && trafficLight.longitude!=null){
                textViewCoords.text = String.format(context.resources.getString(R.string.traffic_light_lat_and_long),
                    trafficLight.longitude!! *100/100, trafficLight.latitude!! *100/100)
            }
            if(trafficLight.alternativeTitle != null){
                textViewAlt.text = String.format(context.resources.getString(R.string.traffic_know_alt), trafficLight.alternativeTitle)
                textViewAlt.visibility = View.VISIBLE
            } else {
                textViewAlt.visibility = View.GONE
            }
            if(trafficLight.createdTime != null){
                textViewCreation.text = trafficLight.createdTime.toString()
                textViewCreation.visibility = View.VISIBLE
            } else {
                textViewCreation.visibility = View.GONE
            }
            if(trafficLight.redColor){
                textViewUpdate.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
            } else {
                textViewUpdate.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            }
            textViewUpdate.setOnClickListener{
                val action = MainFragmentDirections.actionMainFragmentToEditTrafficLight(trafficLight)
                it.findNavController().navigate(action)
            }
        }

        companion object {
            fun create(context:Context, parent: ViewGroup): TrafficLightViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TrafficLightViewHolder(context, view)
            }
        }
    }

    class TrafficLightComparator : DiffUtil.ItemCallback<TrafficLight>() {
        override fun areItemsTheSame(oldItem: TrafficLight, newItem: TrafficLight): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TrafficLight, newItem: TrafficLight): Boolean {
            return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude
        }
    }
}
