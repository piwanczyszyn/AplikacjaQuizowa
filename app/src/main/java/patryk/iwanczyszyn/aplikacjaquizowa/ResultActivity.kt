package patryk.iwanczyszyn.aplikacjaquizowa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //wyłapujemy i ustawiamy dane przekazane z MainActivity -> QuizQuestionsActitity
        val username = intent.getStringExtra(Constants.USER_NAME) //wyłapane imie
        tv_name.text = username

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAnswer = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        tv_score.text = "Twój wynik to $correctAnswer z $totalQuestions"

        // ustawiamy osttani button, by po zakonczeniu przechodził do MainActivity i kończył swoją aktywność
        btn_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}