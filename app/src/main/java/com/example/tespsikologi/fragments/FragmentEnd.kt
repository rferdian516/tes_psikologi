package com.example.tespsikologi.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tespsikologi.MainActivity
import com.example.tespsikologi.R
import com.example.tespsikologi.TesActivity
import com.example.tespsikologi.databinding.FragmentEndBinding
import com.example.tespsikologi.databinding.FragmentHomeBinding
import com.example.tespsikologi.model.User
import com.example.tespsikologi.ui.profile.ProfileFragment
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_end.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.StringBuilder

class FragmentEnd : Fragment() {
    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String
    lateinit var binding: FragmentEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_end, container, false)

        binding.btnMove.setOnClickListener {
            val intent=Intent(this@FragmentEnd.context, MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
//        mDatabase.child(myPreferences.getValue("id")!!).child("nilai_1")


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoading = ProgressDialog(this@FragmentEnd.context)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading ...")

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        myPreferences = MySharedPreferences(this@FragmentEnd.requireContext())
        userId = myPreferences.getValue("id")!!

        readData()

        }

    fun readData(){
        mLoading.show()
        mDatabase.child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@FragmentEnd.context,
                        "${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    mLoading.dismiss()
                    val nilai = snapshot.getValue(User::class.java)
                    tv_nilai1.setText(nilai?.Nilai_1.toString())
                    tv_nilai2.setText(nilai?.Nilai_2.toString())
                    tv_nilai3.setText(nilai?.Nilai_3.toString())
                }
            })
    }

    }





