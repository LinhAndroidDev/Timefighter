package com.example.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var score = 0
    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountDown: Long = 60000
    private var countDownInterval: Long = 1000
    private var timeLeft = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetGame()

        tap_me_button.setOnClickListener {
            incrementScore()
            if (!gameStarted) {
                startGame()
            }
        }
    }

    private fun incrementScore(){
        score++
        val newScore = getString(R.string.your_score,score)
        game_score_text_view.text = newScore
    }

    private fun resetGame(){
        score = 0
        val initialScore = getString(R.string.your_score, score)
        game_score_text_view.text = initialScore
        val initialTimeLeft = getString(R.string.time_left, 60)
        time_left_text_view.text = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown,
            countDownInterval) {
            // 3
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000
                val timeLeftString = getString(R.string.time_left,
                    timeLeft)
                time_left_text_view.text = timeLeftString
            }
            override fun onFinish() {
                // To Be Implemented Later
                endGame()
            }
        }
        // 4
        gameStarted = false
    }

    private fun startGame(){
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame(){
        Toast.makeText(this, getString(R.string.game_over_message, score)
            ,Toast.LENGTH_LONG).show()
        resetGame()
    }
    }

