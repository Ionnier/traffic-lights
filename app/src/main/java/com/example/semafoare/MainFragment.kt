package com.example.semafoare

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semafoare.database.TrafficLight
import com.example.semafoare.database.TrafficLightAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: TrafficLightViewModel by viewModels {
        TrafficLightViewModelFactory((activity?.applicationContext as TrafficLightApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TrafficLightAdapter(context!!)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)

        viewModel.allData().asLiveData().observe(this, {
            // Update the cached copy of the words in the adapter.
            it?.let { adapter.submitList(it) }
        })

        getView()?.findViewById<Button>(R.id.button)?.setOnClickListener {
            context?.let {
                if (ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        it as Activity,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        2
                    )
                } else {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            if(location?.latitude!=null){
                                val action = MainFragmentDirections.actionMainFragmentToAddTrafficLightFragment(location.latitude.toFloat(), location.longitude.toFloat())
                                this.findNavController().navigate(action)
                            } else {
                                context?.let{
                                    Toast.makeText(it, context!!.resources.getString(R.string.location_services_off), Toast.LENGTH_SHORT).show()
                                }
                            }
                                //viewModel.insert(TrafficLight(null, it, location?.longitude, null, true))

                        }
                }
            }
        }
    }
}