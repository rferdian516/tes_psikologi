package com.example.tespsikologi

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tespsikologi.model.User
import com.example.tespsikologi.ui.profile.ProfileFragment
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.fragment_end.*
import kotlinx.android.synthetic.main.fragment_profile.*

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        mLoading = ProgressDialog(this@UpdateProfileActivity)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading ...")

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        myPreferences = MySharedPreferences(this@UpdateProfileActivity)
        userId = myPreferences.getValue("id")!!


        readData()

        btn_back.setOnClickListener {
            onBackPressed()
            finish()
        }

        btn_update.setOnClickListener {
            if (validate()) {
                val mNama = et_nama.text.toString()
                create(mNama)
            }
        }

    }

    private fun readData() {
        mLoading.show()
        mDatabase.child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@UpdateProfileActivity,
                        "${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    mLoading.dismiss()
                    val name = snapshot.getValue(User::class.java)
                    et_nama.setText(name!!.Name)
                }
            })
    }

    private fun validate(): Boolean {
        if (et_nama.text.isEmpty()) {
            et_nama.requestFocus()
            et_nama.error = "Masukan nama"
            return false
        }
        return true
    }

    private fun create(mNama: String) {
        mLoading.show()
        val user = User(userId, mNama)
        mDatabase.child(myPreferences.getValue("id")!!).child("name")
            .setValue(mNama)

        val goMain = Intent(this@UpdateProfileActivity, ProfileFragment::class.java)
        startActivity(goMain)
        finish()

        mLoading.dismiss()
    }
}