package com.example.rps_ch5.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rps_ch5.R
import com.example.rps_ch5.databinding.CustomDialogBinding
import com.example.rps_ch5.fragments.GameFragmentDirections
import com.example.rps_ch5.viewmodel.GameViewModel

class CustomDialog : DialogFragment() {

    private val sharedViewModel: GameViewModel by activityViewModels()

    private var _binding: CustomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = CustomDialogBinding.inflate(LayoutInflater.from(context))

        binding.mainLagiBtn.setOnClickListener {
            sharedViewModel.reset()
            dismiss()
            findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFragment())
        }

        binding.rtnMenuBtn.setOnClickListener {
            sharedViewModel.reset()
            val action = GameFragmentDirections.actionGameFragmentToMenuFragment()
            findNavController().navigate(action)
        }

        winningMsg()
        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            customDialog = this@CustomDialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun winningMsg() {
        if (sharedViewModel.winner == "draw") {
            binding.winningMsg.text = "SERI!"
        } else {
            binding.winningMsg.text = getString(R.string.player_win, sharedViewModel.winner)
        }
    }
}