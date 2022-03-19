package com.example.semafoare

import android.app.AlertDialog
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.semafoare.database.Alternative
import com.example.semafoare.databinding.FragmentEditTrafficLightBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditTrafficLight : Fragment() {

    private val viewModel: TrafficLightViewModel by viewModels {
        TrafficLightViewModelFactory((activity?.applicationContext as TrafficLightApplication).repository)
    }

    private var _binding: FragmentEditTrafficLightBinding? = null
    private val binding get() = _binding!!
    private val args: EditTrafficLightArgs by navArgs()

    private var allAlternatives: List<Alternative>? = null
    private var position: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTrafficLightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allAlternatives().asLiveData().observe(this, {
            it?.let { allAlternatives = it }
        })
        binding.id.text = context?.resources?.let { String.format(it.getString(R.string.id), args.trafficLight.trafficLightId) }
        binding.latitude.text =
            context?.resources?.let { String.format(it.getString(R.string.latitude), args.trafficLight.latitude) }
        binding.longitude.text =
            context?.resources?.let { String.format(it.getString(R.string.longitude), args.trafficLight.longitude) }
        binding.createdAt.text =
            context?.resources?.let { String.format(it.getString(R.string.created_at),args.trafficLight.createdTime.toString()) }
        if(args.trafficLight.alternative != null){
            binding.alternative.text =
                context?.resources?.let { String.format(it.getString(R.string.traffic_know_alt), args.trafficLight.alternative!!.alternativeTitle)}
        } else {
            binding.alternative.visibility = View.INVISIBLE
        }

        binding.editText.setOnClickListener{
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
        updateColorButton()
        binding.button3.setOnClickListener{
            args.trafficLight.redColor = !args.trafficLight.redColor
            updateColorButton()
        }
        binding.button5.setOnClickListener{
            viewModel.delete(args.trafficLight)
            this.findNavController().navigateUp()
        }
        binding.button4.setOnClickListener{
            var alternative: Alternative? = null
            if(position< allAlternatives?.size!!){
                alternative = allAlternatives!![position]
            }
            args.trafficLight.alternative = alternative
            viewModel.update(args.trafficLight)
            this.findNavController().navigateUp()
        }
        Glide.with(this)
            .load("https://api.mapbox.com/styles/v1/mapbox/light-v10/static/${args.trafficLight.longitude},${args.trafficLight.latitude},17/500x300?access_token=pk.eyJ1IjoiaW9ubmllciIsImEiOiJja3huOHNsa28yNjNtMnJrajNpOTQ1bjdqIn0.eIHET3y_HWq9KqJUt-Utjg")
            .into(binding.imageView)

    }

    private fun updateColorButton() {
        if (args.trafficLight.redColor) {
            setButtonColor(binding.button3, Color.RED)
        } else {
            setButtonColor(binding.button3, Color.GREEN)
        }
    }

    companion object{
        fun setButtonColor(btn: Button, color: Int) {
            btn.background.setTint(color)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}