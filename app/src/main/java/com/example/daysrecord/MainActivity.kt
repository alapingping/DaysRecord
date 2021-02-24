package com.example.daysrecord

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.daysrecord.ui.events.EventsFragment
import com.example.daysrecord.ui.home.HomeFragment
import com.example.daysrecord.ui.message.MessageFragment
import com.example.daysrecord.Utils.Companion.showToast
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
        toolbar.inflateMenu(R.menu.menu)

        navigation_view.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.option_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
                    toolbar.menu.findItem(R.id.option_search).isVisible = false
                    toolbar.menu.findItem(R.id.option_new_message).isVisible = false
                }
                R.id.option_event -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, EventsFragment.newInstance()).commit()
                    toolbar.menu.findItem(R.id.option_search).isVisible = true
                    toolbar.menu.findItem(R.id.option_new_message).isVisible = false
                }
                R.id.option_message -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MessageFragment.newInstance()).commit()
                    toolbar.menu.findItem(R.id.option_search).isVisible = false
                    toolbar.menu.findItem(R.id.option_new_message).isVisible = true
                }
                R.id.option_setting -> {
                    // TODO: 打开设置界面

                    "setting".showToast()
                    toolbar.menu.findItem(R.id.option_search).isVisible = false
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_search) {
            // TODO: 打开搜索界面
            return true
        }
        if (item.itemId == R.id.option_new_message) {
            // TODO: 打开新建留言界面
            for (fragment in supportFragmentManager.fragments) {
                if (fragment is MessageFragment) {
                    fragment.startNewMessageActivity()
                }
            }
            return true
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.apply {
            findItem(R.id.option_search).isVisible = false
            findItem(R.id.option_new_message).isVisible = false
        }
        return true
    }
}