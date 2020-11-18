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
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        mLoading = ProgressDialog(this@SignUpActivity)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading...")
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        myPreferences = MySharedPreferences(this@SignUpActivity)

        tvSignIn.setOnClickListener {
            val goSignIn = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(goSignIn)
            finish()
        }

        imgBack.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
            finish()
        }

        btn_SingUp.setOnClickListener {
            if (validate()) {
                val mName = et_name.text.toString()
                val mEmail = et_email.text.toString()
                val mPassword = et_password.text.toString()

                signUp(mName, mEmail, mPassword)
            }
        }
    }

    private fun validate(): Boolean {
        //Mengecek apakah form sudah terisi atau belum
        if (et_name.text.isEmpty()) {
            et_name.requestFocus()
            et_name.error = "Enter your first name"
            return false
        }
        if (et_email.text.isEmpty()) {
            et_email.requestFocus()
            et_email.error = "Enter your email"
            return false
        }
        if (et_password.text.isEmpty()) {
            et_password.requestFocus()
            et_password.error = "Enter your password"
            return false
        }
        return true
    }

    private fun signUp(mName: String, mEmail: String, mPassword: String) {
        //Menampilkan Loading
        mLoading.show()

        //Mengecek apakah email sudah digunakan
        val cekEmail = mDatabase.orderByChild("email").equalTo(mEmail)

        cekEmail.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                mLoading.dismiss()
                Toast.makeText(
                    this@SignUpActivity,
                    "${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null) {
                    //Get Date Time Now
                    val mCurrentTime = SimpleDateFormat("yyyyMMdd:HHmmss", Locale.getDefault())
                        .format(Date())

                    //Mengisi variabel pada model User
                    val user = User(mCurrentTime, mName,  mEmail, mPassword,"0","0","0")
                    mDatabase.child(mCurrentTime).setValue(user)

                    //Menyimpan data ke shared preferences bahwa user telah berhasil masuk
                    myPreferences.setValue("user", "signIn")

                    //Menyimpan data user yang sudah masuk ke shared preferences
                    myPreferences.setValue("id", user.id)
                    myPreferences.setValue("firstname", user.Name)
                    myPreferences.setValue("email", user.Email)
                    myPreferences.setValue("password", user.Password)
                    myPreferences.setValue("nilai_1", user.Nilai_1)
                    myPreferences.setValue("nilai_2", user.Nilai_2)
                    myPreferences.setValue("nilai_3", user.Nilai_3)
//                    myPreferences.setValueInt("image",user.Image)



                    val goMain = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(goMain)
                    finish()

                    mLoading.dismiss()

                } else {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@SignUpActivity,
                        "Email sudah digunakan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


}