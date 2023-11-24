package com.example.foodyapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.foodyapplication.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.navController)

//----------- Eğer kullanıcı bu fragmentlardan birisindeyse toolbar üzerinden arrow'u kaldırmasını sağladım. ------------
        binding.toolbar.setupWithNavController(navHostFragment.navController,
            AppBarConfiguration(setOf(R.id.recipiesFragment,R.id.favoritRecipiesFragment,R.id.foodJokeFragment))
        )
//----------- Eğer kullanıcı bu fragmentlardan birisindeyse toolbar üzerinden arrow'u kaldırmasını sağladım. ------------





    }
// ------------ Bu fonksiyon oluşturduğumuz toolbarın back arrow özelliğini kullanmamızı sağlıyor --------------
    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
// ------------ Bu fonksiyon oluşturduğumuz toolbarın back arrow özelliğini kullanmamızı sağlıyor --------------
}