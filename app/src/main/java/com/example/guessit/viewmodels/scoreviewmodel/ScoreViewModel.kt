package com.example.guessit.viewmodels.scoreviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(finalScore : Int) : ViewModel(){

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain : LiveData<Boolean> get() = _eventPlayAgain

    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int> get() = _score

    init {

        _score.value = finalScore
        Timber.i("Final Score is $finalScore")
    }

    fun onPlayAgain(){
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete(){
        _eventPlayAgain.value = false
    }
}