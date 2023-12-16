package com.example.foodyapplication.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.size.ViewSizeResolver
import com.example.foody.models.FoodRecipe
import com.example.foodyapplication.data.database.RecipesDatabase
import com.example.foodyapplication.data.database.RecipesEntity
import com.example.foodyapplication.util.NetworkResult
import retrofit2.Response

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){

            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty())
            {
                imageView.visibility = View.VISIBLE
            }else if(apiResponse is NetworkResult.Loading){
                imageView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Sucess){
                imageView.visibility = View.INVISIBLE
            }

        }

        @BindingAdapter("readApiResponse2","readDataBase2", requireAll = true)
        @JvmStatic
        fun errorTextVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty())
            {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }else if(apiResponse is NetworkResult.Loading){
                textView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Sucess){
                textView.visibility = View.INVISIBLE
            }
        }
    }

}