package com.example.tespsikologi.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tespsikologi.R
import com.example.tespsikologi.auth.SignInActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*

class onBoarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)


        btn_board.setOnClickListener {
            startActivity(Intent(this@onBoarding,SignInActivity::class.java))
            finish()
        }
    }
}