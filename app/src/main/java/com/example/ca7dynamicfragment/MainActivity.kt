package com.example.ca7dynamicfragment

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       val barFragment = BarFragment()
        val lineFragment = LineFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFrame, barFragment)
            commit()
        }

        findViewById<Button>(R.id.LineButton).setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFrame, lineFragment)
                commit()
            }
        }

        findViewById<Button>(R.id.BarButton).setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFrame, barFragment)
                commit()
            }
        }
    }
}