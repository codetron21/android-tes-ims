package com.codetron.imscodingtest.shirtstore.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codetron.imscodingtest.shirtstore.databinding.ActivityDetailProductBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}