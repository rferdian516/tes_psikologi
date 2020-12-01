package com.example.tespsikologi

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF


class ClusterActivity : AppCompatActivity() {
    lateinit var studentList: ArrayList<Entry>
    lateinit var yearsList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cluster)
        val pieChart = findViewById<PieChart>(R.id.pc_attribut)

        val NoOfEmp = ArrayList<PieEntry>()

        NoOfEmp.add(PieEntry(945f, "2008"))
        NoOfEmp.add(PieEntry(1040f, "2009"))
        NoOfEmp.add(PieEntry(1133f, "2010"))
        NoOfEmp.add(PieEntry(1240f, "2011"))
        NoOfEmp.add(PieEntry(1369f, "2012"))
        NoOfEmp.add(PieEntry(1487f, "2013"))
        NoOfEmp.add(PieEntry(1501f, "2014"))
        NoOfEmp.add(PieEntry(1645f, "2015"))
        NoOfEmp.add(PieEntry(1578f, "2016"))
        NoOfEmp.add(PieEntry(1695f, "2017"))
        val dataSet = PieDataSet(NoOfEmp, "Number Of Employees")

        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.invalidate()
        pieChart.animateXY(5000, 5000)
    }

//    fun getList(): MutableList<PieEntry>? {
//        studentList.add(Entry(40f, 0f))
//        studentList.add(Entry(50f, 0f))
//        studentList.add(Entry(60f, 0f))
//        studentList.add(Entry(70f, 0f))
//        studentList.add(Entry(90f, 0f))
//    }
//
//    fun getYears(): ArrayList<String>{
//        yearsList.add("2012")
//        yearsList.add("2013")
//        yearsList.add("2014")
//        yearsList.add("2015")
//        yearsList.add("2016")
//        return  yearsList
//    }
}