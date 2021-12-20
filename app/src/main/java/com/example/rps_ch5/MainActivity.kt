package com.example.rps_ch5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.rps_ch5.viewmodel.GameViewModel


class MainActivity : AppCompatActivity() {

    private val sharedViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        sharedViewModel.reset()

        /*code below is used to make sure that the app will close when back button is pressed on menuFragment*/
//        when (navController.currentDestination?.id) {
//            R.id.menuFragment -> {
//                // custom behavior here
//                Log.i("fragment", "${supportFragmentManager.findFragmentById(R.id.menuFragment)}")
//                finishAffinity()
//                exitProcess(0)
//            }
//        }

        super.onBackPressed()
    }
}