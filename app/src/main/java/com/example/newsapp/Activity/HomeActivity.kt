package com.example.newsapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.fragment.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment(),"Home")
        setUpDrawer()

    }

    fun setUpDrawer() {
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_business -> replaceFragment(BusinessFragment(), it.title.toString())
                R.id.nav_entertainment -> replaceFragment(EntertainmentFragment(),it.title.toString())
                R.id.nav_general -> replaceFragment(GeneralFragment(), it.title.toString())
                R.id.nav_health -> replaceFragment(HealthFragment(), it.title.toString())
                R.id.nav_science -> replaceFragment(ScienceFragment(), it.title.toString())
                R.id.nav_sports -> replaceFragment(SportsFragment(), it.title.toString())
                R.id.nav_technology -> replaceFragment(TechnologyFragment(), it.title.toString())
//                R.id.nav_notifications ->
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu);
        return super.onCreateOptionsMenu(menu)
    }
    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.constraintLayout, fragment)
        fragmentTransaction.commit()
        binding.drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}