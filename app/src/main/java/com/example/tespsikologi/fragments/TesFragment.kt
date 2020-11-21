package com.example.tespsikologi.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
//import androidx.recyclerview.widget.RecyclerView
//import com.airbnb.lottie.LottieAnimationView
import com.example.tespsikologi.R
import com.example.tespsikologi.TesActivity
import com.example.tespsikologi.databinding.FragmentTesBinding
import com.example.tespsikologi.model.Question
import com.example.tespsikologi.utils.MySharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_tes.*


class TesFragment : Fragment() {
    private lateinit var mDatabase: DatabaseReference
    lateinit var binding: FragmentTesBinding
    lateinit var currentQuestion: Question
    private var questionIndex=0
    lateinit var numbers: String
    val maxNumberOfQuestion=11
    lateinit var answers: ArrayList<String>
    lateinit var selectAnswer: String
    private lateinit var myPreferences: MySharedPreferences
    private lateinit var userId: String
    var score=0

    //list soal
    var question=arrayListOf<Question>(
        //soal1
        Question("1",
            "Saya suka mendengarkan informasi yang ada daripada membaca buku",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal2
        Question("2",
            "Saat saya sendiri, saya biasanya memainkan musik atau lagu atau bernyanyi",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal3
        Question("3",
            "Saat saya berbicara, saya suka mengatakan saya mendengar anda,itu terdengar bagus, itu bunyinya bagus",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal4
        Question("4",
            "Saya tahu hampir semua kata-kata dari apa yang saya dengar",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal5
        Question("5",
            "Ketika mendengar orang lain berbicara, saya biasanya membuat gambar tentang apa yang mereka katakan dalam pikiran saya",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal6
        Question("6",
            "Mudah sekali bagi saya untuk mengobrol dengan orang lain dalam waktu yang lama saat saya berbicara di telepon",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal7
        Question("7",
            "Tanpa musik, hidup amat membosankan",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal8
        Question("8",
            "Saya sangat senang berkumpul biasanya dapat dengan mudah berbicara dengan siapa saja",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal9
        Question("9",
            "Saat mengingat suatu pengalaman, saya seringkali mendengar suara dan berbicara pada diri saya mengenai pengalaman itu",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal10
        Question("10",
            "Saya lebih suka musik daripada seni lukis",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal11
        Question("11",
            "Saya lebih suka berbicara dari pada menulis",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        ),
        //soal12
        Question("12",
            "Saya dapat mengingat dengan mudah apa yang dikatakan orang",
            arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")
        )
    )

    //mengambil data soal dan jawaban dari variabel qustion
    private fun setQuestion() {
        currentQuestion=question[questionIndex]
        answers=ArrayList(currentQuestion.theAnswer)
        numbers= currentQuestion.theNumber
//        answers.shuffle()
        Log.d("ANSWERGROUP", answers[0] + " " + answers[1] + " " + answers[2] + " " + answers[3])
        Log.d("ANSWERREAL", currentQuestion.theAnswer[0])
    }

    //cek jawaban
    private fun checkAnswer(answer: String) {
        if (answer.equals(currentQuestion.theAnswer[0])) {
            score+=4
        } else if (answer.equals(currentQuestion.theAnswer[1])) {
            score+=3
        } else if (answer.equals(currentQuestion.theAnswer[2])) {
            score+=2
        } else if (answer.equals(currentQuestion.theAnswer[3])) {
            score+=1
        }
        questionIndex++

        //kondisi apabila index soal kurang dari maksimal nomor soal yang ditampilkan
        if (questionIndex <= maxNumberOfQuestion) {
            //menjalankan method setQuestion
            setQuestion()
            //refresh ui
            binding.invalidateAll()
        } else {
            //akan menampilkan skor
//            Toast.makeText(activity, score.toString(), Toast.LENGTH_SHORT).show()

//            val intent = Intent(this@TesFragment.context, TesFragment2::class.java)
//            startActivity(intent)

            getScore()
        }

    }

    //method untuk mengambil soal secara acak
//    private fun randomQuestion() {
////        question.shuffle()
//        setQuestion()
//    }

    private fun getScore() {
        mDatabase.child(myPreferences.getValue("id")!!).child("nilai_1")
            .setValue(score.toString())

        Navigation.findNavController(requireView())
            .navigate(R.id.action_tesFragment_to_tesFragment2)

//        if(score > 3){
//            Toast.makeText(activity, "WON", Toast.LENGTH_SHORT).show()
//        }
//        else{
//            Toast.makeText(activity, "LOSE", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabase=FirebaseDatabase.getInstance().getReference("User")
        myPreferences=MySharedPreferences(requireContext())
        userId=myPreferences.getValue("id")!!
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_tes, container, false)
//        randomQuestion()
        setQuestion()
        binding.quiz1=this
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
