package com.example.rps_ch5.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rps_ch5.R
import com.example.rps_ch5.databinding.FragmentGameBinding
import com.example.rps_ch5.dialog.CustomDialog
import com.example.rps_ch5.viewmodel.GameViewModel
import com.google.android.material.imageview.ShapeableImageView

class GameFragment : Fragment() {

    private val sharedViewModel: GameViewModel by activityViewModels()

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var playerChoices: List<ShapeableImageView>
    private lateinit var opponentChoices: List<ShapeableImageView>
    private var allAvailableChoices: MutableList<ShapeableImageView> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            gameFragment = this@GameFragment
        }

        playerChoices = listOf(
            binding.batuPlayer,
            binding.kertasPlayer,
            binding.guntingPlayer
        )

        opponentChoices = listOf(
            binding.batuComputer,
            binding.kertasComputer,
            binding.guntingComputer
        )

        allAvailableChoices.addAll(playerChoices)
        allAvailableChoices.addAll(opponentChoices)

        setTitleColor()

        playGame(sharedViewModel.opponent)
        recreateState()

        binding.refresh.setOnClickListener {
            restartGame(allAvailableChoices)
        }

        binding.close.setOnClickListener {
            restartGame(allAvailableChoices)
            val action = GameFragmentDirections.actionGameFragmentToMenuFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // function to set
    private fun setTitleColor() {
        val title = binding.title
        val spannableString = SpannableString(getString(R.string.title))
        val titleKertas =
            ForegroundColorSpan(getColor(requireActivity(), R.color.title_orange))
        val titleGunting =
            ForegroundColorSpan(getColor(requireActivity(), R.color.title_green))
        val titleBatu =
            ForegroundColorSpan(getColor(requireActivity(), R.color.title_purple))

        spannableString.setSpan(titleKertas, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(titleGunting, 7, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(titleBatu, 15, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        title.text = spannableString
    }

    // function to set the player name in game screen
    fun setPlayerName(): String {
        return getString(R.string.player_name, sharedViewModel.playerName.value)
    }

    // function to set the opponent name in game screen
    fun setOpponentName(): String {
        return when (sharedViewModel.opponent) {
            sharedViewModel.opponents[0] -> getString(
                R.string.opponent_name,
                sharedViewModel.opponents[0]
            )
            else -> getString(R.string.opponent_name, sharedViewModel.opponents[1])
        }
    }

    // function to shorten the code to change view's background color
    private fun View.changeBgColor(@ColorRes id: Int) {
        setBackgroundColor(getColor(requireActivity(), id))
    }

    // function to disable playable button
    private fun freezeState(allAvailableChoices: List<ShapeableImageView>) {
        sharedViewModel.setStatus(false)
        allAvailableChoices.forEach {
            it.isEnabled = sharedViewModel.status
        }
    }

    // function to restart game
    private fun restartGame(allAvailableChoices: List<ShapeableImageView>) {
        sharedViewModel.reset()
        allAvailableChoices.forEach {
            it.isEnabled = sharedViewModel.status
            it.changeBgColor(R.color.transparent)
        }
        binding.result.text = activity?.getString(R.string.start_game)
        binding.result.setTextColor(getColor(requireActivity(), R.color.VS))
        binding.result.changeBgColor(R.color.transparent)
    }

    private fun resultFormat(result: String, bgColor: Int) {
        binding.result.text = result
        binding.result.setTextColor(getColor(requireActivity(), R.color.white))
        binding.result.changeBgColor(bgColor)
    }

    // function format textview id result
    private fun setResult() {
        when (sharedViewModel.result.value) {
            "draw" -> {
                resultFormat(sharedViewModel.result.value!!, R.color.draw)
            }
            "lose" -> {
                resultFormat(
                    getString(R.string.opponent_win, sharedViewModel.opponent),
                    R.color.result
                )
            }
            "win" -> {
                resultFormat(
                    getString(R.string.player_win, sharedViewModel.playerName.value),
                    R.color.result
                )
            }
        }
    }

    // function to create toast
    private fun createToast(player: String, choice: String) {
        Toast.makeText(
            requireContext(),
            "$player Memilih $choice",
            Toast.LENGTH_SHORT
        ).show()
        Log.i(
            "testing",
            "$choice, opponent: ${sharedViewModel.opponentChoice}, player: ${sharedViewModel.playerChoice}"
        )
    }

    // function to highlight all playable button
    private fun selectAll(choices: List<ShapeableImageView>, @ColorRes id: Int) {
        choices.forEach { it.changeBgColor(id) }
    }

    // function to handle configuration changes
    private fun recreateState() {
        if (sharedViewModel.playerSelectedId != 0) {
            view?.findViewById<ShapeableImageView>(sharedViewModel.playerSelectedId)?.changeBgColor(R.color.selected)
            freezeState(playerChoices)
            setResult()
            if (sharedViewModel.opponentSelectedId != 0) {
                view?.findViewById<ShapeableImageView>(sharedViewModel.opponentSelectedId)?.changeBgColor(R.color.selected)
                freezeState(opponentChoices)
                setResult()
            }
        } else if (sharedViewModel.opponentSelectedId != 0) {
            view?.findViewById<ShapeableImageView>(sharedViewModel.opponentSelectedId)?.changeBgColor(R.color.selected)
            freezeState(opponentChoices)
            setResult()
            if (sharedViewModel.playerSelectedId != 0) {
                selectAll(playerChoices, R.color.selected)
                freezeState(playerChoices)
                setResult()
            }
        } else {
            restartGame(allAvailableChoices)
        }
    }

    // function to create dialog
    private fun createDialog() {
        if (sharedViewModel.result.value != "") {
            val dialogFragment = CustomDialog()
            dialogFragment.isCancelable = false
            dialogFragment.show(childFragmentManager, "custom_dialog")
        }
    }

    // function to play the game
    private fun playGame(opponent: String) {
        when (opponent) {
            sharedViewModel.opponents[1] -> {
                playerChoices.forEach {
                    it.setOnClickListener { choice ->
                        sharedViewModel.setPlayerChoice(choice.contentDescription.toString())
                        sharedViewModel.setPlayerSelectedId(choice.id)
                        choice.changeBgColor(R.color.selected)
                        freezeState(playerChoices)
                        sharedViewModel.setOpponentChoice(sharedViewModel.choices.random())
                        opponentChoices.filter { it.contentDescription == sharedViewModel.opponentChoice }[0].changeBgColor(
                            R.color.selected
                        )
                        sharedViewModel.setOpponentSelectedId(opponentChoices.filter { it.contentDescription == sharedViewModel.opponentChoice }[0].id)
                        sharedViewModel.gameResult()
                        setResult()
                        createToast(
                            sharedViewModel.playerName.value!!,
                            sharedViewModel.playerChoice
                        )
                        createToast(sharedViewModel.opponent, sharedViewModel.opponentChoice)
                        createDialog()
                        Log.i("game_info", "player chooses: ${sharedViewModel.playerChoice}")
                        Log.i("game_info", "opponent chooses: ${sharedViewModel.opponentChoice}")
                    }
                }
            }
            sharedViewModel.opponents[0] -> {
                opponentChoices.forEach {
                    it.setOnClickListener { choice ->
                        sharedViewModel.setOpponentChoice(choice.contentDescription.toString())
                        sharedViewModel.setOpponentSelectedId(choice.id)
                        choice.changeBgColor(R.color.selected)
                        freezeState(opponentChoices)
                        sharedViewModel.gameResult()
                        setResult()
                        createToast(sharedViewModel.opponent, sharedViewModel.opponentChoice)
                        createDialog()
                        Log.i("game_info", "opponent chooses: ${sharedViewModel.opponentChoice}")
                    }
                }
                playerChoices.forEach {
                    it.setOnClickListener { choice ->
                        sharedViewModel.setPlayerChoice(choice.contentDescription.toString())
                        sharedViewModel.setPlayerSelectedId(choice.id)
                        choice.changeBgColor(R.color.selected)
                        freezeState(playerChoices)
                        sharedViewModel.gameResult()
                        setResult()
                        createToast(
                            sharedViewModel.playerName.value.toString(),
                            sharedViewModel.playerChoice
                        )
                        createDialog()
                        Log.i("game_info", "player chooses: ${sharedViewModel.playerChoice}")
                    }
                }
            }
        }
    }
}