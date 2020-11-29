package patryk.iwanczyszyn.aplikacjaquizowa

//Tworzymy jeden obiekt, który będzie pobierał wszystkie pytania
object Constants{
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"

    //Tworzymy funkcję, która będzie pobierała wszystkie wartości jako Array Liste
    fun getQuestions(): ArrayList<Question>{
        val questionsList = ArrayList<Question>() // tworzymy zmienną do której będziemy dopisywali pytania ( stworzenie listy pytań )

        // PYTANIE 1
        val que1 = Question(
            1,
            "Jakiego Państwa jest ta flaga?",
            R.drawable.ic_flag_of_argentina,
            "Argentyna",
            "Polska",
            "Armenia",
            "Niemcy",
            1)
        questionsList.add(que1) // dodajemy pytanie 1 do listy

        // PYTANIE 2
        val que2 = Question(
            2,
            "Jakiego Państwa jest ta flaga?",
            R.drawable.ic_flag_of_australia,
            "Argentyna",
            "Australia",
            "Belgia",
            "Wielka Brytania",
            2)
        questionsList.add(que2) // dodajemy pytanie 2 do listy

        // PYTANIE 3
        val que3 = Question(
            3,
            "Jakiego Państwa jest ta flaga?",
            R.drawable.ic_flag_of_belgium,
            "Wenezuela",
            "Włochy",
            "Francja",
            "Belgia",
            4)
        questionsList.add(que3) // dodajemy pytanie 3 do listy

        return questionsList

    }
}