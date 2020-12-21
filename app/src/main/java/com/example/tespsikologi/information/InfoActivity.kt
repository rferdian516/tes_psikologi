package com.example.tespsikologi.information

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.tespsikologi.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val viewPager=findViewById<ViewPager>(R.id.vp_info)
        mViewPager=viewPager
        mViewPager.adapter=InfoAdapter(supportFragmentManager, this)
        mViewPager.offscreenPageLimit=1

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })

        btn_back.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun getItem(i: Int): Int {
        return mViewPager.currentItem + i
    }
}