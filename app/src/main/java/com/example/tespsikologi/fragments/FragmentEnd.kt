package com.example.tespsikologi.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.tespsikologi.MainActivity
import com.example.tespsikologi.R
import com.example.tespsikologi.TesActivity
import com.example.tespsikologi.databinding.FragmentEndBinding
import com.example.tespsikologi.databinding.FragmentHomeBinding
import com.example.tespsikologi.ui.profile.ProfileFragment


class FragmentEnd : Fragment() {

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
    }

}