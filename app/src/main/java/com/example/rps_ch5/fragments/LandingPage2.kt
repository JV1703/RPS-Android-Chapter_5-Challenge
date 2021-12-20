package com.example.rps_ch5.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rps_ch5.R
import com.example.rps_ch5.databinding.FragmentLandingPage2Binding
import com.example.rps_ch5.viewmodel.GameViewModel

private const val IMAGE_URL =
    "https://raw.githubusercontent.com/JV1703/chapter5_asset/master/landing-page2.png"

class LandingPage2 : Fragment() {

    private val sharedViewModel: GameViewModel by activityViewModels()

    private var _binding: FragmentLandingPage2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLandingPage2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = sharedViewModel
        }

        Glide.with(this)
            .load(IMAGE_URL)
            .error(R.drawable.ic_connection_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivMain)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}