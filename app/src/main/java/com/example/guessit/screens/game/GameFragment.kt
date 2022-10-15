package com.example.guessit.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.guessit.R
import com.example.guessit.databinding.GameFragmentBinding
import com.example.guessit.viewmodels.gameviewmodel.GameViewModel
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Timber.i("Called view model provider")
        //getting the reference of the game view model
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)


        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        gameViewModel.score.observe(viewLifecycleOwner, Observer{ newScore ->
//            binding.scoreText.text = newScore.toString()
//
//        })
//
//        gameViewModel.word.observe( viewLifecycleOwner , Observer { newWord ->
//            binding.wordText.text = newWord.toString()
//        })

//        gameViewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
//            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
//
//        })

        gameViewModel.eventGameFinished.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished){
                gameFinished()
                gameViewModel.onGameFinishComplete()
            }

        })


        return binding.root

    }
    /**
     * Called when the game is finished
     */

    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(gameViewModel.score.value ?: 0)
        findNavController(this).navigate(action)

//        Toast.makeText(requireContext(),"Game Finished",Toast.LENGTH_SHORT).show()
    }
}
