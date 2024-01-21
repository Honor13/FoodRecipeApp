package com.example.foodyapplication.ui.fragment.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.models.Result
import com.example.foodyapplication.R
import com.example.foodyapplication.adapters.IngredientsAdapter
import com.example.foodyapplication.databinding.FragmentIngredientsBinding
import com.example.foodyapplication.util.Constants.Companion.RECIPE_RESULT_KEY


class IngredientsFragment : Fragment() {

    private lateinit var binding:FragmentIngredientsBinding

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private lateinit var result: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_ingredients, container, false)

        arguments?.let {
            result = it.getSerializable(RECIPE_RESULT_KEY) as Result
        }

        setupRecyclerView()
        result?.extendedIngredients?.let { mAdapter.setData(it) }

        return binding.root
    }


    private fun setupRecyclerView(){
        binding.ingredientsRecyclerview.adapter = mAdapter
        binding.ingredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }
}