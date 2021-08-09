package com.ciro.montebravotest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.ciro.montebravotest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTxtView.text =  HtmlCompat.fromHtml("Análise de <b>Ações</>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

}