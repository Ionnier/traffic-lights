package com.example.semafoare

import android.app.AlertDialog
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
import android.content.DialogInterface
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.example.semafoare.database.Alternative
import com.example.semafoare.database.AlternativeDao
import android.app.Dialog

import android.widget.EditText
import timber.log.Timber
import java.lang.Exception


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
    private var allAlternatives: List<Alternative>? = null
    private var position: Int = -1

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

        viewModel.allAlternatives().asLiveData().observe(this, {
            it?.let { allAlternatives = it }
        })

        getView()?.findViewById<TextView>(R.id.info)?.text = context?.resources?.let {
            String.format(
                it.getString(R.string.latitude_longitude), args.latitude, args.longitude
            )
        }
        getView()?.findViewById<TextView>(R.id.button)?.setOnClickListener {
            onClickListener(false)
        }
        getView()?.findViewById<TextView>(R.id.button2)?.setOnClickListener {
            onClickListener(true)
        }

        getView()?.findViewById<TextView>(R.id.button6)?.setOnClickListener {
            allAlternatives?.let {
                if (it.isNotEmpty()) {
                    val mBuilder = AlertDialog.Builder(context)
                    mBuilder.setTitle(context?.resources?.getString(R.string.choose_alternative))
                    val options = allAlternatives!!.map { el -> el.alternativeTitle }.toMutableList()
                    options.add("")
                    mBuilder.setSingleChoiceItems(
                        options.toTypedArray(), -1
                    )
                    { dialogInterface, i ->
                        position = i
                        dialogInterface.dismiss();
                    }
                    mBuilder.show()
                }
            }
        }

        getView()?.findViewById<TextView>(R.id.button7)?.setOnClickListener {
            val mBuilder = AlertDialog.Builder(context)
            mBuilder.setTitle(context?.resources?.getString(R.string.add_alternative))
            val inflater = requireActivity().layoutInflater
            mBuilder.setView(inflater.inflate(R.layout.alternative, null))
                .setPositiveButton(android.R.string.ok) { dialog, id ->
                    val text = (dialog as AlertDialog).findViewById<EditText>(R.id.alertTitle).text
                    text?.let{
                        if(text.toString().isNotEmpty()){
                            viewModel.insert(Alternative(null, text.toString()))
                        }
                    }
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            mBuilder.create()
            mBuilder.show()

        }
    }

    private fun onClickListener(redColor: Boolean) {
        try {
            var alternative: Alternative? = null
            if (position < allAlternatives?.size!!) {
                alternative = allAlternatives!![position]
            }
            viewModel.insert(
                TrafficLight(
                    null,
                    String.format("%.6f", args.latitude).toDouble(),
                    String.format("%.6f", args.longitude).toDouble(),
                    alternative,
                    redColor,
                    Date()
                )
            )
        } catch (e: Exception) {
            viewModel.insert(
                TrafficLight(
                    null,
                    String.format("%.6f", args.latitude).toDouble(),
                    String.format("%.6f", args.longitude).toDouble(),
                    null,
                    redColor,
                    Date()
                )
            )

        }

        this.findNavController().navigateUp()
    }
}