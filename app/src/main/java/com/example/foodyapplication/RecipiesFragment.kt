package com.example.foodyapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodyapplication.databinding.FragmentRecipiesBinding

private lateinit var binding:FragmentRecipiesBinding
class RecipiesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_recipies, container, false)


        binding.shimmerRV.showShimmer(true)

        return binding.root
    }


}