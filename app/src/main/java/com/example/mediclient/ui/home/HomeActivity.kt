package com.example.mediclient.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowInsetsControllerCompat
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

        // 상태바 색상 설정
        window.apply {
            statusBarColor = Color.WHITE
            // 상태바 아이콘을 검정색으로 설정 (true: 검정 / false: 흰색)
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true
        }

        val currentFragment = supportFragmentManager.findFragmentById(R.id.rv_home)

        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.rv_home, HomeFragment())
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

        clickTopAppbar()
        clickDrawNavigation()
    }

    // 상단 바 호출을 위한 코드
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top_app_bar, menu)
        return true
    }

    private fun clickTopAppbar() {
        binding.toolbarHome.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.top_mypage -> {
                    val intent = Intent(this, ChatbotActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.top_favorite -> {
                    val intent = Intent(this, ChatbotActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
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
            .replace(R.id.rv_home, fragment)
            .commit()
    }

    private val mockHomeContentList = listOf<HomeContent>(
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "정각원의 소리",
            writer = "동국스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "보신각의 소리",
            writer = "서울스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "봉은사의 소리",
            writer = "강남스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "소림사의 소리",
            writer = "중국스님",
        ),
        HomeContent(
            profileImage = R.drawable.medi_dummy,
            title = "조계사 소리",
            writer = "종로스님",
        )
    )

}
