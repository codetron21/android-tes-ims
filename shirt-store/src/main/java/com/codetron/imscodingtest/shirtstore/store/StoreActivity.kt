package com.codetron.imscodingtest.shirtstore.store

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codetron.imscodingtest.abstraction.AccountManager
import com.codetron.imscodingtest.abstraction.ActivityRouter
import com.codetron.imscodingtest.resources.R
import com.codetron.imscodingtest.shirtstore.cart.CartBottomSheetDialogFragment
import com.codetron.imscodingtest.shirtstore.data.Product
import com.codetron.imscodingtest.shirtstore.data.StoreState
import com.codetron.imscodingtest.shirtstore.databinding.ActivityStoreBinding
import com.codetron.imscodingtest.shirtstore.product.ProductDetailActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding

    private val viewModel: StoreViewModel by viewModel()

    private val accountManager: AccountManager by inject()

    private val router: ActivityRouter by inject()

    private val storeAdapter by lazy {
        StoreAdapter().apply {
            setOnClickListener(this@StoreActivity::navigateToDetail)
        }
    }

    private val categoriesAdapter by lazy {
        CategoriesAdapter().apply {
            setOnClickListener { id ->
                viewModel.selectCategory(id)
                viewModel.getProducts()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            viewModel.getCategories()
            viewModel.getProducts()
        }

        observeViewModel()
        setupView()
    }

    override fun onResume() {
        super.onResume()
        checkCart()
    }

    private fun navigateToDetail(product: Product) {
        startActivity(router.navigateToProductDetail(this).apply {
            putExtra(ProductDetailActivity.EXTRA_DATA, product)
        })
        overridePendingTransition(
            R.anim.anim_slide_right_in,
            R.anim.anim_slide_left_out
        )
    }

    private fun observeViewModel() = with(binding) {
        viewModel.getStateProducts().observe(this@StoreActivity) { state ->
            when (state) {
                is StoreState.Error -> {
                    progressProducts.isVisible = false
                    textErrorProducts.isVisible = true
                }
                StoreState.Loading -> {
                    progressProducts.isVisible = true
                    textErrorProducts.isVisible = false
                    storeAdapter.submitData(emptyList())
                }
                is StoreState.Success -> {
                    progressProducts.isVisible = false
                    textErrorProducts.isVisible = false
                    storeAdapter.submitData(state.data)
                }
            }
        }

        viewModel.getStateCategories().observe(this@StoreActivity) { state ->
            when (state) {
                is StoreState.Error -> {
                    progressCategories.isVisible = false
                    textErrorCategories.isVisible = true
                    categoriesAdapter.updateData(emptyList())
                }
                StoreState.Loading -> {
                    progressCategories.isVisible = true
                    textErrorCategories.isVisible = false
                }
                is StoreState.Success -> {
                    progressCategories.isVisible = false
                    textErrorCategories.isVisible = false
                    categoriesAdapter.updateData(state.data)
                }
            }
        }
    }

    private fun setupView() = with(binding) {
        buttonCart.setOnClickListener {
            CartBottomSheetDialogFragment().run {
                show(supportFragmentManager, null)
                setClickListener { checkCart() }
            }
        }

        buttonBack.setOnClickListener {
            onBackPressed()
        }

        listCategory.layoutManager =
            LinearLayoutManager(this@StoreActivity, RecyclerView.HORIZONTAL, false)
        listCategory.adapter = categoriesAdapter
        listCategory.addItemDecoration(CategoryItemDecoration())

        listProduct.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        listProduct.adapter = storeAdapter
        listProduct.addItemDecoration(StoreItemDecoration())
    }

    private fun checkCart() = with(binding) {
        val color = if (accountManager.getProductsCart().isEmpty()) {
            ContextCompat.getColor(
                this@StoreActivity,
                com.codetron.imscodingtest.resources.R.color.black
            )
        } else {
            ContextCompat.getColor(
                this@StoreActivity,
                com.codetron.imscodingtest.resources.R.color.blue_light
            )
        }
        buttonCart.imageTintList = ColorStateList.valueOf(color)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            com.codetron.imscodingtest.resources.R.anim.anim_slide_left_in,
            com.codetron.imscodingtest.resources.R.anim.anim_slide_right_out
        )
    }

}