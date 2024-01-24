package com.example.foodyapplication.ui.fragment.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyapplication.R
import com.example.foodyapplication.adapters.FavoriteRecipesAdapter
import com.example.foodyapplication.databinding.FragmentFavoritRecipiesBinding
import com.example.foodyapplication.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritRecipiesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritRecipiesBinding

    private val mAdapter: FavoriteRecipesAdapter  by lazy { FavoriteRecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_favorit_recipies,container,false)

        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter


        setupRecyclerView(binding.favoriteRecipesRecyclerView)


        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner){favoritesEntity->
            mAdapter.setData(favoritesEntity)
        }


        return binding.root
    }


    fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}