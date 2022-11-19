package com.codetron.imscodingtest.shirtstore.product

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.codetron.imscodingtest.abstraction.AccountManager
import com.codetron.imscodingtest.shirtstore.R
import com.codetron.imscodingtest.shirtstore.data.Product
import com.codetron.imscodingtest.shirtstore.databinding.ActivityDetailProductBinding
import com.codetron.imscodingtest.shirtstore.util.formatRupiah
import org.koin.android.ext.android.inject

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    private val accountManager: AccountManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupData()
    }

    override fun onResume() {
        super.onResume()
        checkProduct()
    }

    private fun setupData() = with(binding) {
        val data = getData() ?: return@with

        textName.text = data.name

        textDescription.text = data.description ?: getString(R.string.none)

        textPrice.text = data.price?.formatRupiah()

        textCategory.text = data.category?.name
        textCategory.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(data.category?.colorHex ?: "#000000")
        )

        imageProduct.load(data.photoUrl) {
            crossfade(true)
            error(com.codetron.imscodingtest.resources.R.color.grey)
            placeholder(com.codetron.imscodingtest.resources.R.color.blue_light)
            transformations(RoundedCornersTransformation(50F))
        }
    }

    private fun setupView() = with(binding) {
        buttonAddRemoveCart.setOnClickListener {
            val data = getData() ?: return@setOnClickListener
            accountManager.addProductToCart(data.id)
            checkProduct()
        }

        buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            com.codetron.imscodingtest.resources.R.anim.anim_slide_left_in,
            com.codetron.imscodingtest.resources.R.anim.anim_slide_right_out
        )
    }

    private fun checkProduct() = with(binding) {
        val data = getData() ?: return@with
        val isProductAtCart = accountManager.getProductsCart().find { it == data.id }
        val drawable =
            if (isProductAtCart == null) R.drawable.ic_add_cart
            else R.drawable.ic_remove_cart

        buttonAddRemoveCart.setImageResource(drawable)
    }

    private fun getData(): Product? = intent.getParcelableExtra(EXTRA_DATA)

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

}