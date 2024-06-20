package com.example.offerwalltestapp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.offerwalltestapp.databinding.ActivityMainBinding
import com.example.offerwalltestapp.view_models.AppViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var container: FrameLayout? = null
    private var buttonNext: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        container = findViewById(R.id.frameLayout)
        buttonNext = findViewById(R.id.buttonNext)
        buttonNext?.setBackgroundColor(Color.GRAY)

        val viewModel : AppViewModel by viewModels {AppViewModel.Factory}
        viewModel.currentData.observe(this) { data ->

            container?.removeAllViews()

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.gravity = Gravity.CENTER
            var view: View? = null

            if(data.type == "text") {
                view = TextView(this)
                view.text = data.message
                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
            } else if(data.type == "webview") {
                view = WebView(this)
                view.settings.javaScriptEnabled = true
                view.settings.domStorageEnabled = true
                view.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return false
                    }
                }
                view.loadUrl(data.url ?: "")

            } else if(data.type == "image") {
                view = ImageView(this)
                Picasso.with(this).load(data.url).into(view)
            } else return@observe

            view.layoutParams = layoutParams
            container?.addView(view)
        }

        buttonNext?.setOnClickListener {
            viewModel.currentIndex.value = (viewModel.currentIndex.value!! + 1) % viewModel.idArray.size
            viewModel.currentId.value = viewModel.idArray[viewModel.currentIndex.value!!]
            viewModel.getObject(viewModel.currentId.value!!)
        }

        viewModel.buttonNextEnabled.observe(this) { isEnabled ->
            buttonNext?.isEnabled = isEnabled
        }

    }
}