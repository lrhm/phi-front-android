package xyz.lrhm.phiapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.lrhm.LoginQuery
import xyz.lrhm.phiapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val toolbar = binding.mainLayout.toolbar
        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.exerciseGalleryFragment,
                R.id.scheduleDayFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(this)

        binding.mainLayout.bottomNavigationView.setOnNavigationItemSelectedListener { item->
            when (item.itemId) {
                R.id.navBottomEducationalItem -> {
                    Timber.d("clicked education")
                    navController.navigate(R.id.action_global_exerciseGalleryFragment)


                    true
                }
                R.id.navBottomScheduleItem -> {
                    navController.navigate(R.id.action_global_scheduleDayFragment2)

                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }


//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navBottomEducationalItem -> {
//                    Timber.d("clicked education")
//                    navController.navigate(R.id.action_exerciseGalleryFragment_to_scheduleDayFragment)
//
//
//                    true
//                }
//                R.id.navBottomScheduleItem -> {
//                    navController.navigate(R.id.action_scheduleDayFragment_to_exerciseGalleryFragment)
//
//                    // Respond to navigation item 2 click
//                    true
//                }
//                else -> false
//            }
//        }


    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        if (destination.id == R.id.splashFragment) {

            binding.mainLayout.appBar.visibility = View.GONE
        } else {
            binding.mainLayout.appBar.visibility = View.VISIBLE

        }

        if (destination.id == R.id.exerciseGalleryFragment ||
                destination.id == R.id.scheduleDayFragment
                ) {
            binding.mainLayout.bottomNavigationView.visibility = View.VISIBLE
        } else {
            binding.mainLayout.bottomNavigationView.visibility = View.GONE

        }
    }
}