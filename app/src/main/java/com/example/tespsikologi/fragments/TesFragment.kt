package com.example.tespsikologi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.airbnb.lottie.LottieAnimationView
import com.example.tespsikologi.R
import com.example.tespsikologi.databinding.FragmentTesBinding
import com.example.tespsikologi.model.Question
import kotlinx.android.synthetic.main.fragment_tes.*


class TesFragment : Fragment() {
    lateinit var binding: FragmentTesBinding
    lateinit var currentQuestion: Question
    private var questionIndex = 0
    val maxNumberOfQuestion = 36
    lateinit var answers: ArrayList<String>
    lateinit var selectAnswer: String
    var score = 0

    //list soal
    var question = arrayListOf<Question>(
        Question("Saya suka mendengarkan informasi yang ada daripada membaca buku", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Jika saya membaca sesuatu, saya selalu membaca instruksi terlebih dahulu", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya lebih suka membaca daripada mendengarkan guru mengajar", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saat saya sendiri, saya biasanya memainkan musik atau lagu atau bernyanyi", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya lebih suka berolahraga daripada membaca buku", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya selalu dapat menunjukan arah utara atau selatan dimanapun saya berada ", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya suka menulis surat daripada jurnal", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saat saya berbicara, saya suka mengatakan "+"saya mendengar anda,itu terdengar bagus, itu bunyinya bagus", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Ruangan, belajar, kamar atau rumah saya biasanya berantakan", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya suka merancang, mengerjakan, dan membuat sesuatu dengan kedua tangan", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya tahu hampir semua kata-kata dari apa yang saya dengar", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Ketika mendengar orang lain berbicara, saya biasanya membuat gambar tentang apa yang mereka katakan dalam pikiran saya", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya suka olahraga dan saya rasa, saya olahragawan yang baik", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Mudah sekali bagi saya untuk mengobrol dengan orang lain dalam waktu yang lama saat saya berbicara di telepon", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Tanpa musik, hidup amat membosankan", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya sangat senang berkumpul biasanya dapat dengan mudah berbicara dengan siapa saja", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saat melihat objek dalam bentuk gambar, saya dapat dengan mudah mengenali objek yang sama", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya biasanya mengatakan "+" saya menumukan pijakan atas hal itu atau saya ingin bisa menangan hal itu", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saat mengingat pengalaman, saya seringkali melihat pengalaman itu dalam bentuk gambar di dalam pikiran saya", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saat mengingat suatu pengalaman, saya seringkali mendengar suara dan berbicara pada diri saya mengenai pengalaman itu", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saat mengingat suatu pengalaman, saya seringkali ingat bagaimana perasaan saya terhadap pengalaman itu", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya lebih suka musik daripada seni lukis", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya sering mencoret-coret kertas saat berbicara di telepon atau dalam suatu pertemuan", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya lebih suka melakukan contoh peragaan dari pada membuat laporan tertulis atas suatu kejadian", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya lebih suka membacakan cerita dari pada mendengarkan cerita", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya biasanya berbicara dengan perlahan", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya lebih suka berbicara dari pada menulis", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Tulisan tangan saya biasanya tidak rapi", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya biasanya menggunakan jari saya untuk menunjukkan kalimat yang saya baca", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya dapat dengan cepat melakukan penjumlahan dan perkalian dalam pikiran saya", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya suka mengeja dan saya pikir saya pintar mengeja kata-kata", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya akan sangat terganggu apabila ada orang yang berbicara pada saat saya sedang menonton TV", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya suka mencatat perintah atau instruksi yang disampaikan kepada saya", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya dapat mengingat dengan mudah apa yang dikatakan orang", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Saya paling mudah belajar sambil mempratekkan/melakukan", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
        Question("Sangat sulit bagi saya untuk duduk diam dalam waktu yang lama", arrayListOf("Sangat Sesuai", "Sesuai", "Kurang Sesuai", "Tidak Sesuai")),
    )

    //mengambil data soal dan jawaban dari variabel qustion
    private fun setQuestion() {
        currentQuestion = question[questionIndex]
        answers = ArrayList(currentQuestion.theAnswer)
//        answers.shuffle()
        Log.d("ANSWERGROUP", answers[0] + " " + answers[1] + " " + answers[2] + " " + answers[3])
        Log.d("ANSWERREAL", currentQuestion.theAnswer[0])
    }

    //cek jawaban
    private fun checkAnswer(answer: String) {
        if (answer.equals(currentQuestion.theAnswer[0])) {
            score += 4
        }
        else if (answer.equals(currentQuestion.theAnswer[1])) {
            score += 3
        }
        else if (answer.equals(currentQuestion.theAnswer[2])) {
            score += 2
        }
        else if (answer.equals(currentQuestion.theAnswer[3])) {
            score += 1
        }
        questionIndex++

        //kondisi apabila index soal kurang dari maksimal nomor soal yang ditampilkan
        if (questionIndex < maxNumberOfQuestion) {
            //menjalankan method setQuestion
            setQuestion()
            //refresh ui
            binding.invalidateAll()
        } else {
            //akan menampilkan skor
            Toast.makeText(activity, score.toString(), Toast.LENGTH_SHORT).show()
//            getScore()
        }

    }

    //method untuk mengambil soal secara acak
    private fun randomQuestion() {
        question.shuffle()
        setQuestion()
    }

//    private fun getScore(){
//        if(score > 3){
//            Toast.makeText(activity, "WON", Toast.LENGTH_SHORT).show()
//        }
//        else{
//            Toast.makeText(activity, "LOSE", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tes, container, false)
        randomQuestion()
        binding.quiz = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_jawaban_satu.setOnClickListener {
            checkAnswer(tv_jawaban_satu.text.toString())
        }
        tv_jawaban_dua.setOnClickListener {
            checkAnswer(tv_jawaban_dua.text.toString())
        }
        tv_jawaban_tiga.setOnClickListener {
            checkAnswer(tv_jawaban_tiga.text.toString())
        }
        tv_jawaban_empat.setOnClickListener {
            checkAnswer(tv_jawaban_empat.text.toString())
        }

    }

}
