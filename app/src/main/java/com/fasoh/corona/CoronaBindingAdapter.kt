package com.fasoh.corona

import android.widget.TextView
import androidx.databinding.BindingAdapter

class CoronaBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["commaSeparated"])
        fun toUppercaseLetter(textView: TextView, input: String) {
            textView.text = String.format("%,d", input.toLong())
        }




    }

}