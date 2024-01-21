package com.example.foodyapplication.ui.fragment.recipies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodyapplication.viewmodels.MainViewModel
import com.example.foodyapplication.R
import com.example.foodyapplication.adapters.RecipesAdapter
import com.example.foodyapplication.databinding.FragmentRecipiesBinding
import com.example.foodyapplication.util.NetworkListener
import com.example.foodyapplication.util.NetworkResult
import com.example.foodyapplication.util.observeOnce
import com.example.foodyapplication.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class RecipiesFragment : Fragment(){


    private val args by navArgs<RecipiesFragmentArgs>()

    private var _binding: FragmentRecipiesBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    private lateinit var networkListener: NetworkListener

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
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_recipies, container, false
        )
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel // layoutmainViewModel = mainViewModel

        setupRecyclerView()

        binding.searchView.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    searchApiData(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner){
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{status ->
                    Log.d("NetworkListener",status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()

                }
        }

        binding.recipesFab.setOnClickListener{
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipiesFragment_to_recipesBottomSheet)
            }else  {
                recipesViewModel.showNetworkStatus()
            }

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }
    private fun readDatabase() {
       lifecycleScope.launch {
           mainViewModel.readRecipe.observeOnce(viewLifecycleOwner) { database ->
               if (database.isNotEmpty() && !args.backFromBottomSheet ) {
                   Log.d("Recipes Fragment", "readDatabase called")
                   mAdapter.setData(database[0].foodRecipe)
                   hideShimmerEffect()
               } else {
                   requestApiData()
               }
           }
       }
    }

    private fun requestApiData() {
        Log.d("Recipes Fragment", "requestApiData called")
        val recipes = mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Sucess -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
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

        }

    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuerry(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkResult.Sucess ->{
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }

        }
    }


    private fun loadDataFromCache(){
       lifecycleScope.launch {
           mainViewModel.readRecipe.observe(viewLifecycleOwner){database->
               if (database.isNotEmpty()){
                   mAdapter.setData(database[0].foodRecipe)
               }
           }
       }
    }


    private fun showShimmerEffect() {
        binding.shimmerRV.isVisible = true
        binding.recyclerView.visibility = View.INVISIBLE
        binding.shimmerRV.showShimmer(true)
    }

    private fun hideShimmerEffect() {
        binding.shimmerRV.isVisible = false
        binding.recyclerView.visibility = View.VISIBLE
        binding.shimmerRV.hideShimmer()

    }




}