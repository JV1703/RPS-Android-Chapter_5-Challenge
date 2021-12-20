package com.example.rps_ch5.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rps_ch5.R
import com.example.rps_ch5.databinding.FragmentLandingPage3Binding
import com.example.rps_ch5.viewmodel.GameViewModel

private const val IMAGE_URL =
    "https://raw.githubusercontent.com/JV1703/chapter5_asset/master/landing-page3.png"

class LandingPage3 : Fragment() {

    private val sharedViewModel: GameViewModel by activityViewModels()

    private var _binding: FragmentLandingPage3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLandingPage3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            landingpage3Fragment = this@LandingPage3
        }

        Glide.with(this)
            .load(IMAGE_URL)
            .error(R.drawable.ic_connection_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivMain)

        binding.etPlayerName.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userNameInput = binding.etPlayerName.text.toString().trim()
            sharedViewModel.setPlayerName(userNameInput)
            if (userNameInput.isNotBlank()) {
                sharedViewModel.setNextBtnStatus(true)
            } else {
                sharedViewModel.setNextBtnStatus(false)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 10) {
                Toast.makeText(context, "maximum text size is 10 characters", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.etPlayerName.removeTextChangedListener(textWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}