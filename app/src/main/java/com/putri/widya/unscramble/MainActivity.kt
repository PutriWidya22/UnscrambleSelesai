package com.putri.widya.unscramble

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// membuat class utama yaitu MainActivity yang di extands dengan class AppCompatActivity
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}