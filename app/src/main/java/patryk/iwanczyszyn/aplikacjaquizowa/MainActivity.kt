package patryk.iwanczyszyn.aplikacjaquizowa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Wyświetlamy Toasta ( dymek odnoszacy się do nie wprowadzenia imienia w polu )
        btn_start.setOnClickListener{
            if(et_name.text.toString().isEmpty()){
                Toast.makeText(this,"Nie wprowadziłeś / łaś swojego imienia. Popraw to :)", Toast.LENGTH_SHORT).show()
            }else{
                // Jeżeli zostało wprowadzone imię, przechodzimy do kolejnej aktywności (pytań)
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, et_name.text.toString()) // przetwarzamy dane podane w polu IMIĘ do kolejnego okna, zapisując je do USER_NAME
                startActivity(intent)
                finish() //zamykamy aktualną aktywność, by móc wrócić do niej na końcu quizu
            }
        }

    }
}