package com.example.tespsikologi.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tespsikologi.MainActivity
import com.example.tespsikologi.R
import com.example.tespsikologi.model.User
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_in.et_email as et_email1
import kotlinx.android.synthetic.main.activity_sign_up.et_password as et_password1

class SignInActivity : AppCompatActivity() {
    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mLoading = ProgressDialog(this@SignInActivity)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading...")
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        myPreferences = MySharedPreferences(this@SignInActivity)

        if (myPreferences.getValue("user").equals("signIn")) {
            val goMain = Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(goMain)
            finish()
            return //Agar program dibawah line ini tidak dijalankan
        }

        tvSignUp.setOnClickListener {
            val goSignUp = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(goSignUp)
            finish()
        }

        btn_Login.setOnClickListener {
            if (validate()) {
                val mEmail = et_email.text.toString()
                val mPassword = et_password.text.toString()
                signIn(mEmail, mPassword)
            }
        }
    }

    private fun validate(): Boolean {
        if (et_email.text.isEmpty()) {
            et_email.requestFocus()
            et_email.error = "Enter your email"
            return false
        }
        if (et_password.text.isEmpty()) {
            et_password.requestFocus()
            et_email.error = "Enter your password"
            return false
        }
        return true
    }

        private fun signIn(mEmail: String, mPassword: String) {
        //Menampilkan Loading
        mLoading.show()

        //Cek email terdaftar
        val cekEmail = mDatabase.orderByChild("email").equalTo(mEmail)

        cekEmail.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                mLoading.dismiss()
                Toast.makeText(
                    this@SignInActivity,
                    "${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    var user: User? = null

                    for (item in snapshot.children) {
                        //Mengisi variabel pada model User
                        user = item.getValue(User::class.java)
                    }

                    if (user!!.Password == mPassword) {
                        //Menyimpan data ke shared preferences bahwa user telah berhasil masuk
                        myPreferences.setValue("user", "signIn")

                        //Menyimpan data user yang sudah masuk ke shared preferences
                        myPreferences.setValue("id", user.id)
                        myPreferences.setValue("name", user.Name)
                        myPreferences.setValue("email", user.Email)
                        myPreferences.setValue("password", user.Password)
//                        myPreferences.setValueInt("image", user.Image)

                        val goMain = Intent(this@SignInActivity, MainActivity::class.java)
                        startActivity(goMain)
                        finish()

                        mLoading.dismiss()
                    } else {
                        mLoading.dismiss()
                        Toast.makeText(
                            this@SignInActivity,
                            "Password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@SignInActivity,
                        "Email belum terdaftar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}