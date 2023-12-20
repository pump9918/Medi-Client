package com.example.mediclient.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.mediclient.R
import com.example.mediclient.databinding.ActivityHomeBinding
import com.example.mediclient.ui.chatbot.ChatbotActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_home)

        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_home, HomeFragment())
                .commit()
        }

        // Toolbar 설정
        setSupportActionBar(binding.toolbarHome)

        // Drawer toggle 설정
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayoutHome,
            binding.toolbarHome,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close,
        )
        binding.drawerLayoutHome.addDrawerListener(toggle)
        toggle.syncState()

        binding.fabHomeChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }

        clickDrawNavigation()
    }

    private fun clickDrawNavigation() {
        binding.dnvHome.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    binding.drawerLayoutHome.closeDrawers()
                    true
                }

                R.id.menu_chatbot -> {
                    val intent = Intent(this, ChatbotActivity::class.java)
                    startActivity(intent)
                    binding.drawerLayoutHome.closeDrawers()
                    true
                }

                R.id.menu_mypage -> {
                    val intent = Intent(this, ChatbotActivity::class.java) // MypageActivity로 수정
                    startActivity(intent)
                    binding.drawerLayoutHome.closeDrawers()
                    true
                }

                else -> false
            }
            binding.drawerLayoutHome.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayoutHome.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayoutHome.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
    }
}
