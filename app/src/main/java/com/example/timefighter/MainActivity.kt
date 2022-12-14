package com.example.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private var score = 0
    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountDown: Long = 60000
    private var countDownInterval: Long = 1000
    private var timeLeft = 60

    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate called. Score is: $score")

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }

        tap_me_button.setOnClickListener {
            incrementScore()
            if (!gameStarted) {
                startGame()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()
        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time Left: $timeLeft")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called.") }

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
                val timeLeftString = getString(R.string.time_left, timeLeft)
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

    private fun restoreGame() {
        val restoredScore = getString(R.string.your_score, score)
        game_score_text_view.text = restoredScore
        val restoredTime = getString(R.string.time_left, timeLeft)
        time_left_text_view.text = restoredTime

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000
                val timeLeftString = getString(R.string.time_left, timeLeft)
                time_left_text_view.text = timeLeftString
            }

            override fun onFinish() {
                endGame()
            }
        }
            countDownTimer.start()
            gameStarted = true
    }
    }

