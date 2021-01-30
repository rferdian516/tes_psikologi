package com.example.tespsikologi.recyclerview

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tespsikologi.R
import com.example.tespsikologi.model.User
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlin.math.sqrt

class ListActivity : AppCompatActivity() {
    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Initialize variable
        mLoading=ProgressDialog(this@ListActivity)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading ...")

        mDatabase=FirebaseDatabase.getInstance().getReference("User")
        myPreferences=MySharedPreferences(this@ListActivity)
        userId=myPreferences.getValue("id")!!

        showList()

        btn_back.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    fun showList() {
        mLoading.show()
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                mLoading.dismiss()
                Toast.makeText(
                    this@ListActivity,
                    "${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val listData=ArrayList<User>()
                    for (item in snapshot.children) {
                        val user=item.getValue(User::class.java)
                        listData.add(user!!)
                    }
                    do {

                        var cent_tanggap=0.0f
                        var cent_teliti=0.0f
                        var cent_ulet=0.0f
                        var a=0
                        var v=0
                        var k=0

                        //menghitung centroid baru
                        for (n in 0 until listData.size) {
                            if (listData[n].Kelompok == "AUDIOTORI") {
                                cent_tanggap+=listData[n].Hasil_1.toFloat()
                                cent_teliti+=listData[n].Hasil_2.toFloat()
                                cent_ulet+=listData[n].Hasil_3.toFloat()
                                a++
                            } else if (listData[n].Kelompok == "VISUAL") {
                                cent_tanggap+=listData[n].Hasil_1.toFloat()
                                cent_teliti+=listData[n].Hasil_2.toFloat()
                                cent_ulet+=listData[n].Hasil_3.toFloat()
                                v++
                            } else if (listData[n].Kelompok == "KINESTETIK") {
                                cent_tanggap+=listData[n].Hasil_1.toFloat()
                                cent_teliti+=listData[n].Hasil_2.toFloat()
                                cent_ulet+=listData[n].Hasil_3.toFloat()
                                k++
                            }
                        }

                        //inisialisasi centroid baru
                        //cent Audiotori
                        val listCentA=ArrayList<Float>()
                        var n_a1=cent_tanggap / a
                        var n_a2=cent_teliti / a
                        var n_a3=cent_ulet / a
                        listCentA.add(0, n_a1)
                        listCentA.add(1, n_a2)
                        listCentA.add(2, n_a3)
                        //cent Visual
                        val listCentV=ArrayList<Float>()
                        var n_v1=cent_tanggap / v
                        var n_v2=cent_teliti / v
                        var n_v3=cent_ulet / v
                        listCentV.add(0, n_v1)
                        listCentV.add(1, n_v2)
                        listCentV.add(2, n_v3)
                        //cent Kinestetik
                        val listCentK=ArrayList<Float>()
                        var n_k1=cent_tanggap / k
                        var n_k2=cent_teliti / k
                        var n_k3=cent_ulet / k
                        listCentK.add(0, n_k1)
                        listCentK.add(1, n_k2)
                        listCentK.add(2, n_k3)

                        //perhitungan distance
                        var cluster: String=""
                        for (i in 0 until listData.size) {
                            var total1=
                                Math.pow(listData[i].Nilai_1.toDouble() - listCentA[0], 2.0) +
                                        Math.pow(
                                            listData[i].Nilai_2.toDouble() - listCentA[1],
                                            2.0
                                        ) +
                                        Math.pow(listData[i].Nilai_3.toDouble() - listCentA[2], 2.0)
                            var new_dis1=sqrt(total1)
                            mDatabase.child(listData[i].id).child("hasil_1")
                                .setValue(new_dis1.toString())

                            var total2=
                                Math.pow(listData[i].Nilai_1.toDouble() - listCentV[0], 2.0) +
                                        Math.pow(
                                            listData[i].Nilai_2.toDouble() - listCentV[1],
                                            2.0
                                        ) +
                                        Math.pow(listData[i].Nilai_3.toDouble() - listCentV[2], 2.0)
                            var new_dis2=sqrt(total2)
                            mDatabase.child(listData[i].id).child("hasil_2")
                                .setValue(new_dis1.toString())

                            var total3=
                                Math.pow(listData[i].Nilai_1.toDouble() - listCentK[0], 2.0) +
                                        Math.pow(
                                            listData[i].Nilai_2.toDouble() - listCentK[1],
                                            2.0
                                        ) +
                                        Math.pow(listData[i].Nilai_3.toDouble() - listCentK[2], 2.0)
                            var new_dis3=sqrt(total3)
                            mDatabase.child(listData[i].id).child("nilai_3")
                                .setValue(new_dis1.toString())



                            //menghitung jarak terdekat
                            if (new_dis1 < new_dis2 && new_dis1 < new_dis3) {
                                cluster="AUDIOTORI"
                            } else if (new_dis2 < new_dis1 && new_dis2 < new_dis3) {
                                cluster="VISUAL"
                            } else if (new_dis3 < new_dis1 && new_dis3 < new_dis2) {
                                cluster="KINESTETIK"
                            }
                        }


                        //mengecek untuk melakukan iterasi
                        var kondisi : String = ""
                        for (i in 0 until listData.size) {
                            if (listData[i].Kelompok != cluster) {
                                kondisi = "beda"
                            } else {
                                kondisi ="sama"
                                mDatabase.child(listData[i].id)
                                    .child("kelompok")
                                    .setValue(cluster)
                            }
                            }

                        }while (kondisi == "beda")

//                    for (i in 0 until listData.size) {
//                        if (listData[i].Kelompok != cluster) {
//                            mDatabase.child(listData[i].id)
//                                .child("kelompok")
//                                .setValue(cluster)
//                        } else {
//                        }
//
//                    }

//                    tv_show.text = Math.pow(2.0, 5.0).toString()
                            rv_recylcer.apply {
                                layoutManager=LinearLayoutManager(this@ListActivity)
                                adapter=ListAdapter(this@ListActivity, listData)
                            }
                        mLoading.dismiss()
                    } else {
                        Toast.makeText(
                            this@ListActivity,
                            "Anda belum menambahkan koleksi",
                            Toast.LENGTH_SHORT
                        ).show()
                        mLoading.dismiss()
                    }
                }
            })
        }


    }