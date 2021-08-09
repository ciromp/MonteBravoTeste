package com.ciro.montebravotest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.ciro.montebravotest.databinding.ActivitySaibaMaisBinding

class SaibaMaisActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySaibaMaisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saiba_mais)

        binding = ActivitySaibaMaisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val urlString : String? = intent.getStringExtra("url")
        if (urlString != null) {
            Log.d("ddd", urlString)
        }else
        {
            Log.d("ddd", "e null")
        }

        if (urlString != null) {
            binding.webview.settings.javaScriptEnabled = true
            binding.webview.webViewClient = (WebViewClient())
            binding.webview.loadUrl(urlString)
        }

    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request != null) {
                view?.loadUrl(request.url.toString())
            }
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }
    }
}