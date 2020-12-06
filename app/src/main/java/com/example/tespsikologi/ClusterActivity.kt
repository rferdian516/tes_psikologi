package com.example.tespsikologi

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tespsikologi.model.GayaBelajar
import com.example.tespsikologi.model.User
import com.example.tespsikologi.ui.profile.ProfileFragment
import com.example.tespsikologi.utils.MySharedPreferences
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cluster.*
import kotlinx.android.synthetic.main.fragment_end.*
import kotlinx.android.synthetic.main.fragment_end.tv_kelompok
import kotlinx.android.synthetic.main.fragment_profile.*


class ClusterActivity : AppCompatActivity() {

    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String
    val g = GayaBelajar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cluster)

        mLoading = ProgressDialog(this@ClusterActivity)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading ...")

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        myPreferences = MySharedPreferences(this@ClusterActivity)
        userId = myPreferences.getValue("id")!!

        readDataBar()
        getCluster()

        btn_back.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

//    fun readDataPie(){
//        mLoading.show()
//        mDatabase.child(userId)
//            .addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {
//                    mLoading.dismiss()
//                    Toast.makeText(
//                        this@ClusterActivity,
//                        "${error.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    mLoading.dismiss()
//                    val pieChart = findViewById<PieChart>(R.id.pc_attribut)
//                    val nilai = snapshot.getValue(User::class.java)
//
//                    var ketanggapan = (nilai!!.Nilai_1.toFloat()*12/144)*100
//                    var ketelitian = (nilai!!.Nilai_3.toFloat()*12/144)*100
//                    var keuletan = (nilai!!.Nilai_3.toFloat()*12/144)*100
//
//                    val NoOfEmp = ArrayList<PieEntry>()
//                    NoOfEmp.add(PieEntry(ketanggapan, "Ketanggapan"))
//                    NoOfEmp.add(PieEntry(ketelitian, "Ketelitian"))
//                    NoOfEmp.add(PieEntry(keuletan, "Keuletan"))
//                    val dataSet = PieDataSet(NoOfEmp, "ditampilkan dalamm prosentase(%)")
//
//                    dataSet.setDrawIcons(false)
//                    dataSet.sliceSpace = 3f
//                    dataSet.iconsOffset = MPPointF(0F, 40F)
//                    dataSet.selectionShift = 5f
//                    dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
//
//                    val data = PieData(dataSet)
//                    data.setValueTextSize(11f)
//                    data.setValueTextColor(Color.WHITE)
//                    pieChart.data = data
//                    pieChart.highlightValues(null)
//                    pieChart.invalidate()
//                    pieChart.animateXY(5000, 5000)
//                }
//            })
//    }

    fun readDataBar(){
        mLoading.show()
        mDatabase.child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    mLoading.dismiss()
                    Toast.makeText(
                        this@ClusterActivity,
                        "${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    mLoading.dismiss()
                    val barChart = findViewById<BarChart>(R.id.pc_attribut)
                    val nilai = snapshot.getValue(User::class.java)

                    var ketanggapan = (nilai!!.Nilai_1.toFloat()*12/144)*100
                    var ketelitian = (nilai!!.Nilai_2.toFloat()*12/144)*100
                    var keuletan = (nilai!!.Nilai_3.toFloat()*12/144)*100

//                    val NoOfEmp = ArrayList<BarEntry>()
//                    NoOfEmp.add(BarEntry(0.0f,ketanggapan))
//                    NoOfEmp.add(BarEntry(1.0f,ketelitian))
//                    NoOfEmp.add(BarEntry(2.0f,keuletan))
//                    val dataSet = BarDataSet(NoOfEmp, "ditampilkan dalamm prosentase(%)")
//                    dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

                    val N1 = ArrayList<BarEntry>()
                    N1.add(BarEntry(0.0f,33.3f))
                    N1.add(BarEntry(1.0f,ketanggapan))
//                    N1.add(BarEntry(0f,0f))
                    val dataSet = BarDataSet(N1, String.format("%.1f",ketanggapan)+"% Ketanggapan")
                    dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

                    val N2 = ArrayList<BarEntry>()
                    N2.add(BarEntry(2.0f,33.3f))
                    N2.add(BarEntry(3.0f,ketelitian))
                    val dataSet2 = BarDataSet(N2, String.format("%.1f",ketelitian)+"% Ketelitian")
                    dataSet2.setColors(*ColorTemplate.COLORFUL_COLORS)

                    val N3 = ArrayList<BarEntry>()
                    N3.add(BarEntry(4.0f,33.3f))
                    N3.add(BarEntry(5.0f,keuletan))
                    val dataSet3 = BarDataSet(N3, String.format("%.1f",keuletan)+"% Keuletan")
                    dataSet3.setColors(*ColorTemplate.COLORFUL_COLORS)

                    val N4 = ArrayList<BarEntry>()
                    N4.add(BarEntry(0.0f,0f))
                    val dataSet4 = BarDataSet(N4, "")
//                    dataSet4.setColors(*ColorTemplate.COLORFUL_COLORS)

                    val label = ArrayList<String>()
                    label.add("Ketanggapan")
                    label.add("Ketelitian")
                    label.add("Keuletan")
                    val data = BarData(dataSet,dataSet2,dataSet3,dataSet4)
                    barChart.data = data
                    barChart.setData(data)

                    barChart.setFitBars(true)
                    barChart.animateY(2000)

                    barChart.getDescription().setText(" ")
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
                            this@ClusterActivity,
                            error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        mLoading.dismiss()
                        val kelompok = snapshot.getValue(User::class.java)

                        tv_kelompok.text = kelompok?.Kelompok
                        if (kelompok?.Kelompok == "AUDIOTORI")
                        {
                            tv_gayabelajar.text = g.AUDIOTORI
                        }
                        else if (kelompok?.Kelompok == "VISUAL")
                        {
                            tv_gayabelajar.text = g.VISUAL
                        }
                        else if (kelompok?.Kelompok == "KINESTETIK")
                        {
                            tv_gayabelajar.text = g.KINESTETIK
                        }
                    }
                })


        }


}