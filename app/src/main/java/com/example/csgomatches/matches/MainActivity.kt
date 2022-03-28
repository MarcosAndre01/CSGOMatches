package com.example.csgomatches.matches

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.csgomatches.R
import com.example.csgomatches.matches.data.MatchesRepository
import com.example.csgomatches.matches.data.service.MatchesRemoteDataSource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenCreated {
            MatchesRepository(MatchesRemoteDataSource).getMatches()
        }
    }

}