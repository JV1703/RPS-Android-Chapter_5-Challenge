package com.example.rps_ch5.viewpager

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rps_ch5.R
import com.example.rps_ch5.databinding.FragmentViewPagerBinding
import com.example.rps_ch5.fragments.LandingPage1
import com.example.rps_ch5.fragments.LandingPage2
import com.example.rps_ch5.fragments.LandingPage3
import com.example.rps_ch5.viewmodel.GameViewModel
import com.google.android.material.snackbar.Snackbar

class ViewPager : Fragment() {

    private val sharedViewModel: GameViewModel by activityViewModels()

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var fragmentList: List<Fragment>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        fragmentList = arrayListOf(
            LandingPage1(), LandingPage2(), LandingPage3()
        )

        val adapter =
            ViewPagerAdapter(
                fragmentList as ArrayList<Fragment>,
//                requireActivity().supportFragmentManager, /*it will crash when orientation is changed*/
                childFragmentManager,
                lifecycle
            )

        binding.viewPager.adapter = adapter

        binding.indicator.setViewPager(binding.viewPager)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            viewPagerFragment = this@ViewPager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun goToNextScreen() {
        val action = ViewPagerDirections.actionViewPagerToMenuFragment()
        findNavController().navigate(action)
        makeSnackbar()
    }

    @SuppressLint("InflateParams")
    private fun makeSnackbar() {
        val snackbar = Snackbar.make(
            binding.viewPager,
            "",
            Snackbar.LENGTH_SHORT
        )

        val customSnackbar: View = layoutInflater.inflate(R.layout.custom_snack_bar, null)

        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

        snackbarLayout.setPadding(10, 10, 10, 10)

        val btn = customSnackbar.findViewById<Button>(R.id.close_btn)
        val tv = customSnackbar.findViewById<TextView>(R.id.welcome_msg)

        tv.text = getString(R.string.welcome_msg, sharedViewModel.playerName.value)
        btn.setOnClickListener { snackbar.dismiss() }

        snackbarLayout.addView(customSnackbar, 0)
        snackbar.show()
    }
}