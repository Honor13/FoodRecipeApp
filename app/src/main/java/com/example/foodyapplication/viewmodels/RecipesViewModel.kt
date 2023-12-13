package com.example.foodyapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foodyapplication.util.Constants
import com.example.foodyapplication.util.Constants.Companion.API_KEY
import com.example.foodyapplication.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodyapplication.util.Constants.Companion.QUERY_API_KEY
import com.example.foodyapplication.util.Constants.Companion.QUERY_DIET
import com.example.foodyapplication.util.Constants.Companion.QUERY_NUMBER
import com.example.foodyapplication.util.Constants.Companion.QUERY_RECIPE_INGREDIENTS
import com.example.foodyapplication.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {
     fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = ""
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_RECIPE_INGREDIENTS] = "true"

        return queries
    }
}