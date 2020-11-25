package com.example.tespsikologi.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.tespsikologi.R
import com.example.tespsikologi.TesActivity
import com.example.tespsikologi.databinding.FragmentTes2Binding
import com.example.tespsikologi.databinding.FragmentTes3Binding
import com.example.tespsikologi.model.Question
import com.example.tespsikologi.ui.profile.ProfileFragment
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_tes.*

class TesFragment3 : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    lateinit var binding: FragmentTes3Binding
    lateinit var numbers: String
    lateinit var currentQuestion3: Question
    private var questionIndex3=0
    val maxNumberOfQuestion3=11
    lateinit var answers3: ArrayList<String>
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String
    var score3 : Double  = 0.0
    var finalscore3 : Double = 0.0

    var question3=arrayListOf<Question>(
        //soal25
        Question(
            "25",
            "Saya lebih suka berolahraga daripada membaca buku",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal26
        Question(
            "26",
            "Saya selalu dapat menunjukan arah utara atau selatan dimanapun saya berada",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal27
        Question(
            "27",
            "Ruangan, belajar, kamar atau rumah saya biasanya berantakan",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal28
        Question(
            "28",
            "Saya suka merancang, mengerjakan, dan membuat sesuatu dengan kedua tangan",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal29
        Question(
            "29",
            "Saya suka olahraga dan saya rasa, saya olahragawan yang baik",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal30
        Question(
            "30",
            "Saya biasanya mengatakan saya menumukan pijakan atas hal itu atau saya ingin bisa menangan hal itu",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal31
        Question(
            "31",
            "Saat mengingat suatu pengalaman, saya seringkali ingat bagaimana perasaan saya terhadap pengalaman itu",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal32
        Question(
            "32",
            "Saya lebih suka melakukan contoh peragaan dari pada membuat laporan tertulis atas suatu kejadian",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal33
        Question(
            "33",
            "Tulisan tangan saya biasanya tidak rapi",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal34
        Question(
            "34",
            "Saya biasanya menggunakan jari saya untuk menunjukkan kalimat yang saya baca",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal35
        Question(
            "35",
            "Saya paling mudah belajar sambil mempratekkan/melakukan",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal36
        Question(
            "36",
            "Sangat sulit bagi saya untuk duduk diam dalam waktu yang lama",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        )
    )

    private fun setQuestion() {
        currentQuestion3=question3[questionIndex3]
        answers3=ArrayList(currentQuestion3.theAnswer)
        numbers=currentQuestion3.theNumber

        Log.d(
            "ANSWERGROUP",
            answers3[0] + " " + answers3[1] + " " + answers3[2] + " " + answers3[3]
        )
        Log.d("ANSWERREAL", currentQuestion3.theAnswer[0])
    }

    private fun checkAnswer(answer: String) {
        if (answer.equals(currentQuestion3.theAnswer[0])) {
            score3+=4
        } else if (answer.equals(currentQuestion3.theAnswer[1])) {
            score3+=3
        } else if (answer.equals(currentQuestion3.theAnswer[2])) {
            score3+=2
        } else if (answer.equals(currentQuestion3.theAnswer[3])) {
            score3+=1
        }
        questionIndex3++

        if (questionIndex3 <= maxNumberOfQuestion3) {
            //menjalankan method setQuestion
            setQuestion()
            //refresh ui
            binding.invalidateAll()
        } else {
            getScore()
        }
    }

    private fun getScore() {
        //rata-rata attribut 1
        finalscore3=score3 / 12
        mDatabase.child(myPreferences.getValue("id")!!).child("nilai_3")
            .setValue(finalscore3.toString())

        Navigation.findNavController(requireView())
            .navigate(R.id.action_tesFragment3_to_fragmentEnd)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mDatabase=FirebaseDatabase.getInstance().getReference("User")
        myPreferences=MySharedPreferences(requireContext())
        userId=myPreferences.getValue("id")!!
        setQuestion()
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_tes3, container, false)
        binding.quiz3=this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_jawaban_satu.setOnClickListener {
            checkAnswer(btn_jawaban_satu.text.toString())
        }
        btn_jawaban_dua.setOnClickListener {
            checkAnswer(btn_jawaban_dua.text.toString())
        }
        btn_jawaban_tiga.setOnClickListener {
            checkAnswer(btn_jawaban_tiga.text.toString())
        }
        btn_jawaban_empat.setOnClickListener {
            checkAnswer(btn_jawaban_empat.text.toString())
        }
    }
}
