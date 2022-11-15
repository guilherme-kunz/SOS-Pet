package guilhermekunz.com.br.sospet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = binding.mainActivityBottomNavigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainActivityfragmentContainerView) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.adoptionFragment -> {
                    if (R.id.adoptionFragment != navHostFragment.navController.currentDestination?.id) {
                        navHostFragment.navController.navigate(
                            R.id.adoptionFragment,
                            null
                        )
                    }
                }
                R.id.profileFragment -> {
                    if (R.id.adoptionFragment != navHostFragment.navController.currentDestination?.id) {
                        navHostFragment.navController.navigate(
                            R.id.profileFragment,
                            null
                        )
                    }
                }
                R.id.rescueFragment -> {
                    if (R.id.rescueFragment != navHostFragment.navController.currentDestination?.id) {
                        navHostFragment.navController.navigate(
                            R.id.rescueFragment,
                            null
                        )
                    }
                }
                R.id.wantedFragment -> {
                    if (R.id.wantedFragment != navHostFragment.navController.currentDestination?.id) {
                        navHostFragment.navController.navigate(
                            R.id.wantedFragment,
                            null
                        )
                    }
                }
            }
            true
        }
    }

}