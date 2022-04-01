package com.example.csgomatches.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.csgomatches.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CSGOMatches)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}