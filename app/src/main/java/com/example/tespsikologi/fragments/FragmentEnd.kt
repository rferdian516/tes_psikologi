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
import com.example.tespsikologi.model.Centroid
import com.example.tespsikologi.model.User
import com.example.tespsikologi.ui.profile.ProfileFragment
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_end.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.StringBuilder
import kotlin.math.pow
import kotlin.math.sqrt

class FragmentEnd : Fragment() {
    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String
    lateinit var binding: FragmentEndBinding
    val c = Centroid()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_end, container, false)

        binding.btnHome.setOnClickListener {
            val intent=Intent(this@FragmentEnd.context, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnUlangi.setOnClickListener {
            val intent = Intent(this@FragmentEnd.context, TesActivity::class.java)
            startActivity(intent)
        }
        return binding.root
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
        getCluster()

        }

    fun readData(){
        mLoading.show()
        mDatabase.child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@FragmentEnd.context,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    mLoading.dismiss()
                    val nilai = snapshot.getValue(User::class.java)
//                    tv_nilai1.setText(nilai?.Nilai_1)
//                    tv_nilai2.setText(nilai?.Nilai_2)
//                    tv_nilai3.setText(nilai?.Nilai_3)

                    //nilai C1
                    var A = (nilai!!.Nilai_1.toDouble()-c.C11)*(nilai!!.Nilai_1.toDouble()-c.C11)
                    var B = (nilai!!.Nilai_2.toDouble()-c.C12)*(nilai!!.Nilai_2.toDouble()-c.C12)
                    var C = (nilai!!.Nilai_3.toDouble()-c.C13)*(nilai!!.Nilai_3.toDouble()-c.C13)
                    //perhitungan Distance
                    var C1: Double = sqrt(A+B+C)
                    //input C1
                    mDatabase.child(myPreferences.getValue("id")!!).child("hasil_1")
                        .setValue(C1.toString())


                    //nilai C2
                    var D = (nilai!!.Nilai_1.toDouble()-c.C21)*(nilai!!.Nilai_1.toDouble()-c.C21)
                    var E = (nilai!!.Nilai_2.toDouble()-c.C22)*(nilai!!.Nilai_2.toDouble()-c.C22)
                    var F = (nilai!!.Nilai_3.toDouble()-c.C23)*(nilai!!.Nilai_3.toDouble()-c.C23)
                    //perhitungan Distance
                    var C2: Double = sqrt(D+E+F)
                    //input C2
                    mDatabase.child(myPreferences.getValue("id")!!).child("hasil_2")
                        .setValue(C2.toString())

                    //nilai C2
                    var G = (nilai!!.Nilai_1.toDouble()-c.C31)*(nilai!!.Nilai_1.toDouble()-c.C31)
                    var H = (nilai!!.Nilai_2.toDouble()-c.C32)*(nilai!!.Nilai_2.toDouble()-c.C32)
                    var I = (nilai!!.Nilai_3.toDouble()-c.C33)*(nilai!!.Nilai_3.toDouble()-c.C33)
                    //perhitungan Distance
                    var C3: Double = sqrt(G+H+I)
                    //input C3
                    mDatabase.child(myPreferences.getValue("id")!!).child("hasil_3")
                        .setValue(C3.toString())
                }
            })


    }

    fun getCluster(){
        mLoading.show()
        mDatabase.child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@FragmentEnd.context,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    mLoading.dismiss()
                    val kelompok = snapshot.getValue(User::class.java)

                    if (kelompok!!.Hasil_1.toDouble() < kelompok!!.Hasil_2.toDouble() && kelompok!!.Hasil_1.toDouble() < kelompok!!.Hasil_3.toDouble() )
                    {
                        mDatabase.child(myPreferences.getValue("id")!!).child("kelompok")
                            .setValue("AUDIOTORI")
                        tv_kelompok.setText(kelompok.Kelompok)
                    }
                    else if (kelompok!!.Hasil_2.toDouble() < kelompok!!.Hasil_1.toDouble() && kelompok!!.Hasil_2.toDouble() < kelompok!!.Hasil_3.toDouble() )
                    {
                        mDatabase.child(myPreferences.getValue("id")!!).child("kelompok")
                            .setValue("VISUAL")
                        tv_kelompok.setText(kelompok.Kelompok)
                    }
                    else if (kelompok!!.Hasil_3.toDouble() < kelompok!!.Hasil_1.toDouble() && kelompok!!.Hasil_3.toDouble() < kelompok!!.Hasil_2.toDouble() )
                    {
                        mDatabase.child(myPreferences.getValue("id")!!).child("kelompok")
                            .setValue("KINESTETIK")
                        tv_kelompok.setText(kelompok.Kelompok)
                    }


                }
            })


    }





    }






