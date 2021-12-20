package com.example.rps_ch5.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _playerName = MutableLiveData("")
    val playerName: LiveData<String> get() = _playerName

    private val _nextBtnStatus = MutableLiveData(false)
    val nextBtnStatus: LiveData<Boolean> get() = _nextBtnStatus

    private val _choices: List<String> = listOf("Batu", "Kertas", "Gunting")
    val choices get() = _choices

    private val _opponents: List<String> = listOf("Pemain 2", "CPU")
    val opponents get() = _opponents

    private var _winner: String = ""
    val winner get() = _winner

    private var _status: Boolean = true
    val status get() = _status

    private var _playerChoice = ""
    val playerChoice get() = _playerChoice

    private var _opponentChoice: String = ""
    val opponentChoice get() = _opponentChoice

    private var _playerSelectedId = 0
    val playerSelectedId get() = _playerSelectedId

    private var _opponentSelectedId = 0
    val opponentSelectedId get() = _opponentSelectedId

    private var _opponent: String = ""
    val opponent get() = _opponent

    private var _result = MutableLiveData("")
    val result: LiveData<String> get() = _result

    fun setPlayerName(playerName: String) {
        _playerName.value = playerName
    }

    fun setPlayerSelectedId(id: Int) {
        _playerSelectedId = id
    }

    fun setOpponentSelectedId(id: Int) {
        _opponentSelectedId = id
    }

    fun setPlayerChoice(choice: String) {
        _playerChoice = choice
    }

    fun setOpponentChoice(choice: String) {
        _opponentChoice = choice
    }

    fun setNextBtnStatus(condition: Boolean) {
        _nextBtnStatus.value = condition
    }

    fun setOpponent(opponent: String) {
        _opponent = opponent
    }

    fun setStatus(status: Boolean) {
        _status = status
    }

    fun gameResult() {
        _result.value = if (playerChoice == opponentChoice) {
            _winner = "draw"
            "draw"
        } else if (playerChoice == choices[0] && opponentChoice == choices[2] ||
            playerChoice == choices[1] && opponentChoice == choices[0] ||
            playerChoice == choices[2] && opponentChoice == choices[1]
        ) {
            _winner = playerName.value.toString()
            "win"
        } else if (playerChoice == choices[0] && opponentChoice == choices[1] ||
            playerChoice == choices[1] && opponentChoice == choices[2] ||
            playerChoice == choices[2] && opponentChoice == choices[0]
        ) {
            _winner = opponent
            "lose"
        } else {
            ""
        }
        Log.d(
            "game_result",
            "Player: ${playerChoice}, Opponent: $opponentChoice - Result: ${result.value}"
        )
        Log.d("game_result", "$opponentSelectedId")
    }

    fun reset() {
        setOpponentChoice("")
        setPlayerChoice("")
        setOpponentSelectedId(0)
        setPlayerSelectedId(0)
        setStatus(true)
    }
}