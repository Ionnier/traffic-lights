package com.example.semafoare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.semafoare.database.TrafficLight
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTrafficLightFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTrafficLightFragment : Fragment() {

    private val viewModel: TrafficLightViewModel by viewModels {
        TrafficLightViewModelFactory((activity?.applicationContext as TrafficLightApplication).repository)
    }

    private val args: AddTrafficLightFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_traffic_light, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getView()?.findViewById<TextView>(R.id.info)?.text= context?.resources?.let {
            String.format(
                it.getString(R.string.latitude_longitude), args.latitude, args.longitude)
        }
        getView()?.findViewById<TextView>(R.id.button)?.setOnClickListener{
            onClickListener(false)
        }
        getView()?.findViewById<TextView>(R.id.button2)?.setOnClickListener{
            onClickListener(true)
        }
    }

    private fun onClickListener(redColor: Boolean) {
        val text = if (view?.findViewById<TextView>(R.id.editText)?.text.isNullOrBlank())
            null
        else
            view?.findViewById<TextView>(R.id.editText)?.text as String?
        viewModel.insert(
            TrafficLight(
                null,
                String.format("%.6f", args.latitude).toDouble(),
                String.format("%.6f", args.longitude).toDouble(),
                text,
                redColor,
                Date()
            )
        )
        this.findNavController().navigateUp()
    }
}