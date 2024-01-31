package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat


class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition:Int =1
    private var mQuestionList:ArrayList<Question>? = null
    private var mSelectedOptionPosition:Int = 0
    private var mCorrectAnswer:Int = 0

    private var mUserName:String? = null

    private var progressbar:ProgressBar? =null
    private var tvprogress:TextView? = null
    private var tvQuestion :TextView? = null
    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null
    private var btnsubmit:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        progressbar = findViewById(R.id.progessBar)
        tvprogress = findViewById(R.id.tvprigress)
        tvQuestion = findViewById(R.id.tvQuestion)
        tvOptionOne = findViewById(R.id.tvoptionOne)
        tvOptionTwo = findViewById(R.id.tvoptionTwo)
        tvOptionThree = findViewById(R.id.tvoptionThree)
        tvOptionFour = findViewById(R.id.tvoptionFour)
        btnsubmit = findViewById(R.id.btn_submit)


        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnsubmit?.setOnClickListener(this)


        mQuestionList = Constants.getQuestion()


        setQuestion()


    }

    private fun setQuestion() {


        defaultOptionView()


        val question: Question = mQuestionList!![mCurrentPosition - 1]
        progressbar?.progress = mCurrentPosition
        tvprogress?.text = "$mCurrentPosition" ///${progressbar?.max}
       tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if(mCurrentPosition == mQuestionList!!.size){
            btnsubmit?.text = "finish"
        }else{
            btnsubmit?.text = "Next"
        }

    }

    private fun defaultOptionView(){
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0,it)
        }
        tvOptionTwo?.let {
            options.add(1,it)
        }

        tvOptionThree?.let {
            options.add(2,it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }

        for( option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.option_border_
            )
        }

    }


    private fun selectedOptionView(tv:TextView,selctedOptionNum:Int){
       defaultOptionView()

       mSelectedOptionPosition = selctedOptionNum
       tv.setTextColor(Color.parseColor("#363A43"))
       tv.setTypeface(tv.typeface,Typeface.BOLD)
       tv.background =  ContextCompat.getDrawable(
           this,
           R.drawable.selected_option_border_bg
       )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvoptionOne -> {
                tvOptionOne?.let {
                    selectedOptionView(it,1)
                }
            }

            R.id.tvoptionTwo -> {
                tvOptionTwo?.let {
                    selectedOptionView(it,2)
                }
            }

            R.id.tvoptionThree -> {
                tvOptionThree?.let {
                    selectedOptionView(it,3)
                }
            }

            R.id.tvoptionFour -> {
                tvOptionFour?.let {
                    selectedOptionView(it,4)
                }
            }

            R.id.btn_submit ->{
               if(mSelectedOptionPosition==0){
                   mCurrentPosition++

                   when{
                       mCurrentPosition<= mQuestionList!!.size->{
                           setQuestion()
                       }

                       else->{

                           val intent = Intent(this,ResultActivity::class.java)
                           intent.putExtra(Constants.USER_NAME,mUserName)
                           intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswer)
                           intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionList?.size)
                           startActivity(intent)
                           finish()

                       }


                   }
               }else{
                   val question = mQuestionList?.get(mCurrentPosition-1)
                   if(question!!.correctAnswer!= mSelectedOptionPosition){
                       answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                   }else{
                       mCorrectAnswer++
                   }
                   answerView(question!!.correctAnswer,R.drawable.correct_option_border_bg)

                   if(mCurrentPosition==mQuestionList!!.size){
                       btnsubmit?.text = "Finish"
                   }else{
                       btnsubmit?.text = "Go to next question"
                   }
                   mSelectedOptionPosition =0
               }
            }
        }
    }

    private fun answerView(answer:Int,drawableView:Int){
        when(answer){
            1->{
                tvOptionOne?.background = ContextCompat.getDrawable(
                   this,drawableView
                )
            }

            2->{
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            3->{
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            4->{
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
        }
    }
}