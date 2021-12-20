package com.example.rps_ch5.binding_adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.rps_ch5.R

//@BindingAdapter("selected")
//fun selected(shapeableImageView: ShapeableImageView, selected: Boolean) {
//    shapeableImageView.setOnClickListener {
//        if (selected) {
//            !selected
//        } else {
//            selected
//        }
//        Log.i("trialll", "$selected")
//    }
//}

@BindingAdapter("choice")
fun choice(imageView: ImageView, choice: String?) {
    when (choice.toString()) {
        "Batu" -> imageView.setImageResource(R.drawable.batu)
        "Kertas" -> imageView.setImageResource(R.drawable.kertas)
        "Gunting" -> imageView.setImageResource(R.drawable.gunting)
    }
    Log.i("TEstinggg", "$choice")
}
