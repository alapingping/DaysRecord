package com.example.daysrecord

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.daysrecord.ui.events.EventsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout.openDrawer(GravityCompat.START)
        val drawertoggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.app_name,
            R.string.app_name
        )
        drawertoggle.syncState()

        setSupportActionBar(toolbar)

        toolbar.inflateMenu(R.menu.menu)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.option_home -> {
                "home".showToast()
            }
            R.id.option_event -> {
                "event".showToast()
            }
            R.id.option_message -> {
                "message".showToast()
            }
            R.id.option_setting -> {
                "setting".showToast()
            }
        }
//        drawerLayout.closeDrawers()
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