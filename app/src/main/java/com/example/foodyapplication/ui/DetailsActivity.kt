package com.example.foodyapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.navArgs
import com.example.foodyapplication.R
import com.example.foodyapplication.adapters.PagerAdapter
import com.example.foodyapplication.databinding.ActivityDetailsBinding
import com.example.foodyapplication.ui.fragment.ingredients.IngredientsFragment
import com.example.foodyapplication.ui.fragment.instructions.InstructionsFragment
import com.example.foodyapplication.ui.fragment.overview.OverviewFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_details)


        setSupportActionBar(binding.detailsToolbar)
        binding.detailsToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")




        val bundle = Bundle().apply {
            putSerializable("recipeBundle",args.result)
        }


        val adapter = PagerAdapter(this,bundle,titles,fragments)

        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}