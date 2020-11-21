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
import com.example.tespsikologi.databinding.FragmentTes2Binding
import com.example.tespsikologi.model.Question
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_tes.*
import kotlinx.coroutines.processNextEventInCurrentThread


class TesFragment2 : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    lateinit var binding: FragmentTes2Binding
    lateinit var currentQuestion2: Question
    lateinit var numbers: String
    private var questionIndex2=0
    val maxNumberOfQuestion2=11
    lateinit var answers2: ArrayList<String>
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String
    var score2=0

    var question2=arrayListOf<Question>(
        //soal13
        Question(
            "13",
            "Jika saya membaca sesuatu, saya selalu membaca instruksi terlebih dahulu",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal14
        Question(
            "14",
            "Saya lebih suka membaca daripada mendengarkan guru mengajar",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal15
        Question(
            "15",
            "Saya suka menulis surat daripada jurnal",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal16
        Question(
            "16",
            "Saat melihat objek dalam bentuk gambar, saya dapat dengan mudah mengenali objek yang sama",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal17
        Question(
            "17",
            "Saat mengingat pengalaman, saya seringkali melihat pengalaman itu dalam bentuk gambar di dalam pikiran saya",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal18
        Question(
            "18",
            "Saya sering mencoret-coret kertas saat berbicara di telepon atau dalam suatu pertemuan",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal19
        Question(
            "19",
            "Saya lebih suka membacakan cerita dari pada mendengarkan cerita",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal20
        Question(
            "20",
            "Saya biasanya berbicara dengan perlahan",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal21
        Question(
            "21",
            "Saya dapat dengan cepat melakukan penjumlahan dan perkalian dalam pikiran saya",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal22
        Question(
            "22",
            "Saya suka mengeja dan saya pikir saya pintar mengeja kata-kata",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal23
        Question(
            "23",
            "Saya akan sangat terganggu apabila ada orang yang berbicara pada saat saya sedang menonton TV",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal24
        Question(
            "24",
            "Saya suka mencatat perintah atau instruksi yang disampaikan kepada saya",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        )
    )

    private fun setQuestion() {

        currentQuestion2=question2[questionIndex2]
        answers2=ArrayList(currentQuestion2.theAnswer)
        numbers=currentQuestion2.theNumber

        Log.d(
            "ANSWERGROUP",
            answers2[0] + " " + answers2[1] + " " + answers2[2] + " " + answers2[3]
        )
        Log.d("ANSWERREAL", currentQuestion2.theAnswer[0])
    }

    private fun checkAnswer(answer: String) {
        if (answer.equals(currentQuestion2.theAnswer[0])) {
            score2+=4
        } else if (answer.equals(currentQuestion2.theAnswer[1])) {
            score2+=3
        } else if (answer.equals(currentQuestion2.theAnswer[2])) {
            score2+=2
        } else if (answer.equals(currentQuestion2.theAnswer[3])) {
            score2+=1
        }
        questionIndex2++

        if (questionIndex2 <= maxNumberOfQuestion2) {
            setQuestion()
            binding.invalidateAll()
        } else {
            getScore()
        }
    }

    private fun getScore() {
        mDatabase.child(myPreferences.getValue("id")!!).child("nilai_2")
            .setValue(score2.toString())

        Navigation.findNavController(requireView())
            .navigate(R.id.action_tesFragment2_to_tesFragment3)
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
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_tes2, container, false)
        binding.quiz2=this
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