package com.example.tespsikologi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.tespsikologi.R
import com.example.tespsikologi.TesActivity
import com.example.tespsikologi.databinding.FragmentHomeBinding
import com.example.tespsikologi.fragments.TesFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        binding.btnMulai.setOnClickListener {
            val intent = Intent(this@HomeFragment.context, TesActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        btn_mulai.setOnClickListener {
//            val intent = Intent(this@HomeFragment.context, TesActivity::class.java)
//            startActivity(intent)
//
//        }
    }

