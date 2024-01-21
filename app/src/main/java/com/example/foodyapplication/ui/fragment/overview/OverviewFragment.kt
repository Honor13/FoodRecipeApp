package com.example.foodyapplication.ui.fragment.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.foody.models.Result
import com.example.foodyapplication.R
import com.example.foodyapplication.databinding.FragmentOverviewBinding
import com.example.foodyapplication.ui.DetailsActivity
import com.example.foodyapplication.ui.DetailsActivityArgs
import com.example.foodyapplication.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup
import java.io.Serializable

class OverviewFragment : Fragment() {

    private lateinit var binding : FragmentOverviewBinding
    private lateinit var result: Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_overview,container,false)
// İlgili fragment içinde aşağıdaki gibi navArgs() kullanarak args'ı alabilirsiniz

        arguments?.let {
            result = it.getSerializable(RECIPE_RESULT_KEY) as Result
        }
        binding.mainImageView.load(result?.image)
        binding.titleTextView.text = result.title
        binding.likesTextView.text = result.aggregateLikes.toString()
        binding.timeTextView.text = result.readyInMinutes.toString()
        result.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.summaryTextView.text = summary
        }

        if (result.vegetarian == true){
            binding.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }

        if (result.vegan == true){
            binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (result.glutenFree == true){
            binding.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (result.dairyFree == true){
            binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (result.veryHealthy == true){
            binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
        if (result.cheap == true){
            binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }


        return binding.root
    }

}