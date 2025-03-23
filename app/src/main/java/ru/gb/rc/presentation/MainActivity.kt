package ru.gb.rc.presentation

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.gb.rc.R
import ru.gb.rc.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.nav_home -> supportActionBar?.title = getString(R.string.menu_home)
                R.id.nav_settings -> supportActionBar?.title = getString(R.string.menu_settings)
                R.id.nav_support -> supportActionBar?.title = getString(R.string.menu_support)
                R.id.nav_mode -> supportActionBar?.title = getString(R.string.menu_mode)
                R.id.editDeviceFragment -> supportActionBar?.title = getString(R.string.edit_device)
                R.id.pultDeviceFragment -> supportActionBar?.title = getString(R.string.pult_device)
                R.id.settingsDeviceFragment -> supportActionBar?.title = getString(R.string.edit_settings)
//                R.id.nav_settings -> supportActionBar?.title = getString(R.string.menu_settings)
//                R.id.nav_settings -> supportActionBar?.title = getString(R.string.menu_settings)



                else -> supportActionBar?.title = getString(R.string.take_photo)
            }
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_settings, R.id.nav_support, R.id.nav_mode
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.home_menu, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}