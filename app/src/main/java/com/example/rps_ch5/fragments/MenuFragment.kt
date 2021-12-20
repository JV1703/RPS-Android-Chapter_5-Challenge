package com.example.rps_ch5.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rps_ch5.R
import com.example.rps_ch5.databinding.FragmentMenuBinding
import com.example.rps_ch5.viewmodel.GameViewModel

object IMGURL {

    const val pvp =
        "https://raw.githubusercontent.com/JV1703/chapter5_asset/master/landing-page1.png"

    const val pvc =
        "https://raw.githubusercontent.com/JV1703/chapter5_asset/master/landing-page2.png"

}


class MenuFragment : Fragment() {

    private val sharedViewModel: GameViewModel by activityViewModels()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            menuFragment = this@MenuFragment
        }

        Glide.with(this)
            .load(IMGURL.pvp)
            .error(R.drawable.ic_connection_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.pvpImg)

        Glide.with(this)
            .load(IMGURL.pvc)
            .error(R.drawable.ic_connection_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.pvcImg)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setPvpString(): String {
        return getString(R.string.pvp_msg, sharedViewModel.playerName.value)
    }

    fun setPvcString(): String {
        return getString(R.string.pvc_msg, sharedViewModel.playerName.value)
    }

    fun vsHuman() {
        val action = MenuFragmentDirections.actionMenuFragmentToGameFragment()
        findNavController().navigate(action)
        sharedViewModel.setOpponent(sharedViewModel.opponents[0])
    }

    fun vsComputer() {
        val action = MenuFragmentDirections.actionMenuFragmentToGameFragment()
        findNavController().navigate(action)
        sharedViewModel.setOpponent(sharedViewModel.opponents[1])
    }
}