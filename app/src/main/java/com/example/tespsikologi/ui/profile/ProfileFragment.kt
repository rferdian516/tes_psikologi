package com.example.tespsikologi.ui.profile

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.tespsikologi.ClusterActivity
import com.example.tespsikologi.R
import com.example.tespsikologi.UpdateProfileActivity
import com.example.tespsikologi.auth.SignInActivity
import com.example.tespsikologi.information.InfoActivity
import com.example.tespsikologi.information.InfoAdapter
import com.example.tespsikologi.model.User
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.fragment_end.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.NullPointerException

class ProfileFragment : Fragment() {
    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String
    private lateinit var profileViewModel: ProfileViewModel
    private val PHOTO = 1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {


        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root


    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoading = ProgressDialog(this@ProfileFragment.context)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading ...")

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        myPreferences = MySharedPreferences(this@ProfileFragment.context!!)
        userId = myPreferences.getValue("id")!!

        readData()
        btn_Logout.setOnClickListener {
            /// Menyimpan data bahwa user telah berhasil masuk
            myPreferences.setValue("user", "")
            // Menyimpan data user yang sudah masuk
            myPreferences.setValue("id", "")
            myPreferences.setValue("name", "")
            myPreferences.setValue("email", "")
            myPreferences.setValue("password", "")
            startActivity(Intent(this@ProfileFragment.context, SignInActivity::class.java))
            requireActivity().finish()
        }

        rlv_Info.setOnClickListener{
            startActivity(Intent(this@ProfileFragment.context, InfoActivity::class.java))
        }

        rlv_Hasil.setOnClickListener{
            startActivity(Intent(this@ProfileFragment.context, ClusterActivity::class.java))
        }

        rlv_Edit.setOnClickListener {
            startActivity(Intent(this@ProfileFragment.context,UpdateProfileActivity::class.java))
        }
    }

    //read Username
    private fun readData(){
        mLoading.show()
        mDatabase.child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@ProfileFragment.context,
                        "${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    mLoading.dismiss()
                    val user = snapshot.getValue(User::class.java)
//                    var nama = user?.Name

//                    tvUsername.text = "$nama"
//                    tvUsername.setText(user!!.Name)
//                    mDatabase.child(userId).child("name").setValue(tvUsername.text)
                    try {
                        tvUsername.text =user!!.Name
                    }catch (ignored : NullPointerException){

                    }

                    Log.d("ProfileFragment", user.toString())



//                    val nUserName = tvUsername.text
//                    if (tvUsername.text == null){
//                        tvUsername.text = "name?.Name"
//                    } else
//                    {
//                        tvUsername.text = name?.Name
//                    }

                }
            })
    }


    }


