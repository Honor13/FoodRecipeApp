package com.example.foodyapplication.ui.fragment.recipies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodyapplication.viewmodels.MainViewModel
import com.example.foodyapplication.R
import com.example.foodyapplication.adapters.RecipesAdapter
import com.example.foodyapplication.databinding.FragmentRecipiesBinding
import com.example.foodyapplication.util.Constants.Companion.API_KEY
import com.example.foodyapplication.util.NetworkResult
import com.example.foodyapplication.util.observeOnce
import com.example.foodyapplication.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipiesFragment : Fragment() {

    private lateinit var binding: FragmentRecipiesBinding
    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_recipies, container, false
        )



        setupRecyclerView()
        readDatabase()

        return binding.root
    }
    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }
    private fun readDatabase() {
       lifecycleScope.launch {
           mainViewModel.readRecipe.observeOnce(viewLifecycleOwner, { database ->
               if (database.isNotEmpty()) {
                   Log.d("Recipes Fragment", "readDatabase called")
                   mAdapter.setData(database[0].foodRecipe)
                   hideShimmerEffect()
               } else {
                   requestApiData()
               }
           })
       }
    }

    private fun requestApiData() {
        Log.d("Recipes Fragment", "requestApiData called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Sucess -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()

                }
            }

        })

    }


    private fun loadDataFromCache(){
       lifecycleScope.launch {
           mainViewModel.readRecipe.observe(viewLifecycleOwner,{database->
               if (database.isNotEmpty()){
                   mAdapter.setData(database[0].foodRecipe)
               }
           })
       }
    }


    private fun showShimmerEffect() {
        binding.shimmerRV.isVisible = true
        binding.shimmerRV.startShimmer()
    }

    private fun hideShimmerEffect() {
        binding.shimmerRV.stopShimmer()
        binding.shimmerRV.isVisible = false
    }


}