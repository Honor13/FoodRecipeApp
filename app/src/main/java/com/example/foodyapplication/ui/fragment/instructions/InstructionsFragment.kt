package com.example.foodyapplication.ui.fragment.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.foody.models.Result
import com.example.foodyapplication.R
import com.example.foodyapplication.databinding.FragmentInstructionsBinding
import com.example.foodyapplication.util.Constants


class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding
    private lateinit var result: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_instructions,container,false)

        arguments?.let {
            result = it.getSerializable(Constants.RECIPE_RESULT_KEY) as Result
        }

        binding.instructionsWebView.webViewClient = object  : WebViewClient() {}
        val websiteUrl: String = result.sourceUrl
        binding.instructionsWebView.loadUrl(websiteUrl)

        return binding.root
    }


}