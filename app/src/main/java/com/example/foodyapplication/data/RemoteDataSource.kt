package com.example.foodyapplication.data

import com.example.foody.models.FoodRecipe
import com.example.foodyapplication.data.network.FoodRecipesApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipies(queries : Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)

    }
}