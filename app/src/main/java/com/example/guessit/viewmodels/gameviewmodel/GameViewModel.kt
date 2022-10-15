package com.example.guessit.viewmodels.gameviewmodel

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel(){

    companion object{
        //This is when the game is over
        private const val DONE = 0L

        //This is the number of milliseconds
        private const val ONE_SEC = 1000L

        //This is the total time of the game
        private const val COUNTDOWN_TIME = 60000L

    }

    // Making the countdown timer
    private val timer : CountDownTimer
    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    //The boolean used to state that game is finished or not
    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished : LiveData<Boolean> get() = _eventGameFinished

    // Timer counts
    private val _currentTime = MutableLiveData<Long>()
    val currentTime : LiveData<Long> get() = _currentTime

    val currentTimeString = Transformations.map(currentTime){ time->
        DateUtils.formatElapsedTime(time)

    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Timber.i("game view model created")
        resetList()
        nextWord()

        _score.value = 0
        _word.value = wordList.removeAt(0)
        _eventGameFinished.value = false

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SEC){
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished/ONE_SEC)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinished.value = true
            }
        }
        timer.start()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
//            _eventGameFinished.value = true
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
         _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Game view Model destroyed")
    }

    fun onGameFinishComplete(){
        _eventGameFinished.value = false
        timer.cancel()
    }
}