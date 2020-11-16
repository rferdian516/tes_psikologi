package com.example.tespsikologi

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tespsikologi.auth.SignInActivity
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // Initialize variable
        mLoading = ProgressDialog(this@SettingActivity)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading ...")

        myPreferences = MySharedPreferences(this@SettingActivity)
        userId = myPreferences.getValue("id")!!


        lnrLogOut.setOnClickListener {
            /// Menyimpan data bahwa user telah berhasil masuk
            myPreferences.setValue("user", "")

            // Menyimpan data user yang sudah masuk
            myPreferences.setValue("id", "")
            myPreferences.setValue("Name", "")
            myPreferences.setValue("Email", "")
            myPreferences.setValue("Password", "")

            startActivity(Intent(this@SettingActivity, SignInActivity::class.java))
            finish()
        }
    }
}