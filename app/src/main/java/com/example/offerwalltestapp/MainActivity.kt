package com.example.offerwalltestapp

import android.graphics.Color
import android.os.Bundle
import android.text.Layout.Alignment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.example.offerwalltestapp.databinding.ActivityMainBinding
import com.example.offerwalltestapp.view_models.AppViewModel

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        val viewModel : AppViewModel by viewModels {AppViewModel.Factory}
        viewModel.currentData.observe(this) { data ->
            if(data.type == "text") {
                val view = TextView(this)
                view.text = data.message
                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(Color.RED)
                view.setBackgroundColor(Color.RED)

                binding?.frameLayout?.removeAllViews()
                binding?.frameLayout?.addView(view, FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
                Log.d("myLogs", "type is text ${data.message}")
            }
            for(view in binding?.frameLayout?.children!!) {
                Log.d("myLogs", (view as TextView).text.toString())
            }
            //Log.d("myLogs", "changed")
        }

    }
}