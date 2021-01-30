package com.example.tespsikologi.recyclerview

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tespsikologi.R
import com.example.tespsikologi.model.User
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList
import kotlinx.android.synthetic.main.item_list.*

class ListAdapter (var mContext: Context, var mData: ArrayList<User>) : RecyclerView.Adapter<ListAdapter.ViewHolder>(){

    private lateinit var mLoading: ProgressDialog
    private lateinit var mDatabase: DatabaseReference
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)  {
        var mNama = v.findViewById<TextView>(R.id.tv_listnama)
        var mKelompok = v.findViewById<TextView>(R.id.tv_listkelompok)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_list,parent, false)

        //Initialize variable
        mLoading = ProgressDialog(mContext)
        mLoading.setCancelable(false)
        mLoading.setMessage("Loading ...")
        mDatabase = FirebaseDatabase.getInstance().getReference("Koleksi")
        myPreferences = MySharedPreferences(mContext)
        userId = myPreferences.getValue("id")!!

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mNama.text =mData[position].Name
        holder.mKelompok.text = mData[position].Kelompok
    }

    override fun getItemCount(): Int = mData.size
}