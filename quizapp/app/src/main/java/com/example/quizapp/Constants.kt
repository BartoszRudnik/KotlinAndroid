package com.example.quizapp

object Constants {

    fun getQuestions() : ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            "Argentina",
            "Australia",
            "Austria",
            "Oman",
            0
        )
        val que2 = Question(
            1,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            "Chile",
            "Peru",
            "Columbia",
            "Brazil",
            3
        )
        val que3 = Question(
            2,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            "Ireland",
            "Australia",
            "Austria",
            "France",
            1
        )
        val que4 = Question(
            3,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_fiji,
            "Czechia",
            "Honduras",
            "Japan",
            "Fiji",
            3
        )
        val que5 = Question(
            4,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_germany,
            "Argentina",
            "Germany",
            "Poland",
            "Russia",
            1
        )

        questionsList.add(que1)
        questionsList.add(que2)
        questionsList.add(que3)
        questionsList.add(que4)
        questionsList.add(que5)

        return questionsList
    }
}