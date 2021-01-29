package com.example.daysrecord

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.daysrecord.ui.events.EventsFragment
import com.example.daysrecord.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawertoggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.app_name,
            R.string.app_name
        )
        drawertoggle.syncState()

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigation_view.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.option_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
                }
                R.id.option_event -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, EventsFragment.newInstance()).commit()
                }
                R.id.option_message -> {
                    "message".showToast()
                }
                R.id.option_setting -> {
                    "setting".showToast()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show()
                exitTime = System.currentTimeMillis()
            } else {
                ActivityCollector.finishAll()
                this.finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}