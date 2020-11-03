package com.example.tespsikologi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.lottie.LottieAnimationView
import com.example.tespsikologi.R

class TesFragment : Fragment() {
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var image: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = requireArguments().getString(ARG_PARAM1)!!
            description = requireArguments().getString(ARG_PARAM2)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootLayout: View = inflater.inflate(R.layout.fragment_tes, container, false)

        tvTitle = rootLayout.findViewById(R.id.text_onboarding_title)

        tvDescription = rootLayout.findViewById(R.id.text_onboarding_description)
        tvTitle.text = title
        tvDescription.text = description
        return rootLayout
    }

    companion object {

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(
            title: String?,
            description: String?
        ): TesFragment {
            val fragment = TesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, title)
            args.putString(ARG_PARAM2, description)
            fragment.arguments = args
            return fragment
        }
    }
}