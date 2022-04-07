package com.example.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var buttonSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        buttonSubmit = findViewById(R.id.btn_next)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        buttonSubmit?.setOnClickListener(this)

        mQuestionList = Constants.getQuestions()

        if(mCurrentPosition == mQuestionList!!.size){
            buttonSubmit?.text = "FINISH"
        }else{
            buttonSubmit?.text = "SUBMIT"
        }

        setQuestion()
        defaultOptionsView()
    }

    private fun setQuestion() {
        val question: Question = mQuestionList!![mCurrentPosition - 1]

        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition / ${mQuestionList!!.size}"
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
        ivImage?.setImageResource(question.image)
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        tvOptionOne?.let {
            options.add(it)
        }
        tvOptionTwo?.let {
            options.add(it)
        }
        tvOptionThree?.let {
            options.add(it)
        }
        tvOptionFour?.let {
            options.add(it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNumber

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border
        )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one ->  {
                tvOptionOne?.let { selectedOptionView(it, 1) }
            }
            R.id.tv_option_two ->  {
                tvOptionTwo?.let { selectedOptionView(it, 2) }
            }
            R.id.tv_option_three ->  {
                tvOptionThree?.let { selectedOptionView(it, 3) }
            }
            R.id.tv_option_four ->  {
                tvOptionFour?.let { selectedOptionView(it, 4) }
            }
        }
    }
}