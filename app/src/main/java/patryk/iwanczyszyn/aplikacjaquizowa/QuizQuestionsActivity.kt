package patryk.iwanczyszyn.aplikacjaquizowa

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition:Int = 1 // zaczynająca pozycja, żebyśmy za każdym razem gdy wejdziemy w tą ACTIVITY, zaczynali od pierwszego pytania
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0 // musimy wiedzieć co osoba wybrała
    private var mCorrectAnswers: Int = 0 // licznik by sprawdzić ile razy odpowiedzielismy poprawnie
    private var mUserName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME) // zapisujemy imię z MainActivity w zmiennej


        mQuestionsList = Constants.getQuestions()

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)


    }

    //Funkcja ustawiająca pytania
    private fun setQuestion(){
        // USTAWIAMY ELEMENTY NA DANEJ ACTIVITY
        val question = mQuestionsList!![mCurrentPosition - 1] // -1 gdyż pytanie jest pod indeksem "0"

        defaultOptionsView() // by sie upewnić że każda opcja ma wartość początkową

        //ustawiamy tu by przy każdym przejściu do kolejnego pytania button wracał do wersji "SPRAWDź", aż do końca puli pytań
        if(mCurrentPosition == mQuestionsList!!.size){
            btn_submit.text = "KONIEC"
        }else{
            btn_submit.text = "SPRAWDŹ"
        }

        //ustawiamy progressbar i licznik przy nim "0/3 itd.."
        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max


        tv_question.text = question!!.question //Ustawiamy pytanie
        iv_image.setImageResource(question.image) // Ustawiamy obrazek

        //ustawiamy  dostepne opcje odpowiedzi
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    //Funkcja określająca wartości początkowe opcji - DEFAULT
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0,tv_option_one)
        options.add(1,tv_option_two)
        options.add(2,tv_option_three)
        options.add(3,tv_option_four)

        // określa jakie obramowanie i tekst ma się pojawiać domyślnie z activity_quiz_questions.xml
        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT // okreslamy jak ma być to wyświetlane (bold etc..) w tym wypadku DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg) // background w tym wypadku obramowanie które "narysowałem" jako default - wartość początkowa

        }
    }
    // Nadpisujemy wartości, w taki sposób jaki mają się pokazać po kliknięciu opcji okreslonej w funkcji selectedOptionView (wygląd)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one ->{
                selectedOptionView(tv_option_one, 1)
            }
            R.id.tv_option_two ->{
                selectedOptionView(tv_option_two, 2)
            }
            R.id.tv_option_three ->{
                selectedOptionView(tv_option_three, 3)
            }
            R.id.tv_option_four ->{
                selectedOptionView(tv_option_four, 4)
            }
            R.id.btn_submit ->{
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    //kolejne pytanie, aż do końca
                    when{
                        mCurrentPosition <= mQuestionsList!!.size->{
                            setQuestion()
                        }else->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName) // przekazujemy dodatkowo imię "dalej"
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers) // przekazujemy dodatkowo ilosc odpowiedzi prawidlowych "dalej"
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size) // przekazujemy dodatkowo ilość wszystkich pytań dostepnych "dalej"
                            startActivity(intent)
                            finish()
                            // TEST: Toast.makeText(this,"Ukończyłeś/łaś wszystkie pytania :)", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    // w innym wypadku sprawdzamy poprawność i nadajemy kolory poprawnej i złej odpowiedzi
                    val question = mQuestionsList?.get(mCurrentPosition -1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg) // poza ifem, gdyż musi się pojawić zawsze niezaleznie od wybranej odpowiedzi
                    if(mCurrentPosition == mQuestionsList!!.size){ // jeżeli skonczyliśmy całą pulę zmieniamy button na KONIEC
                        btn_submit.text = "KONIEC"
                    }else{
                        btn_submit.text = "KOLEJNE PYTANIE" // jeżeli nie skończyliśmy całej puli pytań, zmieniamy button na KOLEJNE PYTANIE
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    // Funkcja dbająca o to by dobrze dobrac kolor w przypadku odpowiedzi złej / dobrej
    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 ->{
                tv_option_one.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 ->{
                tv_option_two.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 ->{
                tv_option_three.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 ->{
                tv_option_four.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    //funkcja okreslająca wybraną opcję przez użytkownika
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView() // resetujemy kliknięcia do wersji początkowej
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD) // pogrubiamy tekst kliknięty
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg) // background w tym wypadku obramowanie które "narysowałem" jako selected - wartość po kliknięciu
    }
}