package guilhermekunz.com.br.sospet.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.ActivityMainBinding
import guilhermekunz.com.br.sospet.ui.authentication.AuthenticationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupBottomNavigation()
        verifyUser()
    }


    // Criar Dialog
    private fun verifyUser() {
        val user = firebaseAuth.currentUser
        if (user == null) {
            val intent = Intent(applicationContext, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }
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